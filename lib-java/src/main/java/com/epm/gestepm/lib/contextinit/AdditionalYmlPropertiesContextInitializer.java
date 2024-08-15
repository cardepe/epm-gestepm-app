package com.epm.gestepm.lib.contextinit;

import static java.util.Optional.ofNullable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;

public class AdditionalYmlPropertiesContextInitializer
        implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final String PATHS_TO_LOAD_PROPERTY = "context.initializer.paths-to-load";

    @Override
    public void initialize(final ConfigurableApplicationContext applicationContext) {

        final List<Resource> resourcesToLoad = new ArrayList<>();
        final List<String> sources = this.getPathsToLoad(applicationContext);

        try {

            for (final String source : sources) {
                resourcesToLoad.addAll(Arrays.asList(applicationContext.getResources(source)));
            }

            final Consumer<Resource> loadToContext = resource -> {

                final YamlPropertySourceLoader yamlPropertySourceLoader = new YamlPropertySourceLoader();

                final List<PropertySource<?>> load;

                try {

                    load = yamlPropertySourceLoader.load(resource.getDescription(), resource);
                    load.forEach(item -> applicationContext.getEnvironment().getPropertySources().addLast(item));

                } catch (final IOException ioe) {
                    throw new ContextInitException(ioe.getMessage(), ioe);
                }
            };

            resourcesToLoad.forEach(loadToContext);

        } catch (final IOException ioe) {
            throw new ContextInitException(ioe.getMessage(), ioe);
        }
    }

    private List<String> getPathsToLoad(final ConfigurableApplicationContext applicationContext) {

        List<String> result = new ArrayList<>();

        final ConfigurableEnvironment environment = applicationContext.getEnvironment();
        final String property = environment.getProperty(PATHS_TO_LOAD_PROPERTY);

        final Function<String, String[]> splitFunction = p -> p.split(",");

        result = ofNullable(property).map(splitFunction).map(Arrays::asList).orElse(result);
        result = result.stream().map(String::trim).collect(Collectors.toList());

        return result;
    }

}
