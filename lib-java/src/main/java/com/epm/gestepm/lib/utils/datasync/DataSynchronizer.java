package com.epm.gestepm.lib.utils.datasync;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

/**
 * Clase auxiliar para la sincronización de datos.
 *
 * <p>
 * Realiza el emparejamiento de dos listas de datos en base a una función de clave. Determina si
 * dicho emparejamiento es una creación, actualización o eliminación.
 *
 * <p>
 * Permite indicar trasnformaciones de los datos en los tres casos mencionados además de indicar la
 * acción a realizar con el resultado de la sincronización tanto en bloque como de forma individual
 * para cada dato.
 *
 * @param <T> Tipo de dato a sincronizar
 * @param <K> Clave por la que identificar dos datos iguales
 */
public class DataSynchronizer<T, K> {

    private final Set<DataSyncMatchOptions> matchOptions;

    private List<T> withData;

    private List<T> syncFrom;

    private Function<T, K> keyMatcher;

    private Predicate<DataSyncMatch<K, T>> isCreate;

    private UnaryOperator<T> transformCreation;

    private Consumer<List<T>> bulkCreationAction;

    private UnaryOperator<T> onSingleCreationAction;

    private Predicate<DataSyncMatch<K, T>> isDelete;

    private UnaryOperator<T> transformDeletion;

    private Consumer<List<T>> bulkDeletionAction;

    private UnaryOperator<T> onSingleDeletionAction;

    private Predicate<DataSyncMatch<K, T>> isUpdate;

    private Function<DataSyncMatch<K, T>, T> transformUpdate;

    private Consumer<List<T>> bulkUpdateAction;

    private UnaryOperator<T> onSingleUpdateAction;

    private Predicate<DataSyncMatch<K, T>> isEqual;

    private Function<DataSyncMatch<K, T>, T> transformEqual;

    private Consumer<List<T>> bulkEqualAction;

    private UnaryOperator<T> onSingleEqualAction;

    public DataSynchronizer() {

        this.withData = new ArrayList<>();
        this.syncFrom = new ArrayList<>();

        this.isCreate = m -> m.getLeftValue() == null && m.getRightValue() != null;
        this.transformCreation = v -> v;
        this.onSingleCreationAction = v -> v;
        this.bulkCreationAction = l -> {
        };

        this.isDelete = m -> m.getLeftValue() != null && m.getRightValue() == null;
        this.transformDeletion = v -> v;
        this.onSingleDeletionAction = v -> v;
        this.bulkDeletionAction = l -> {
        };

        this.isUpdate = m -> false;
        this.transformUpdate = DataSyncMatch::getLeftValue;
        this.onSingleUpdateAction = v -> v;
        this.bulkUpdateAction = l -> {
        };

        this.isEqual = m -> true;
        this.transformEqual = DataSyncMatch::getLeftValue;
        this.onSingleEqualAction = v -> v;
        this.bulkEqualAction = l -> {
        };

        this.matchOptions = new HashSet<>();
    }

    public DataSynchronizer<T, K> matchFor(DataSyncMatchOptions matchOption) {
        matchOptions.add(matchOption);
        return this;
    }

    /**
     * Establece la lista de valores actuales.
     * @param withData Lista de valores actuales
     * @return DataSynchronizer actual
     */
    public DataSynchronizer<T, K> withData(final List<T> withData) {
        this.withData = withData;
        return this;
    }

    /**
     * Establece la lista de valores con los que realizar la sincronización.
     * @param syncFrom Lista de valores con los que realizar la sincronización
     * @return DataSynchronizer actual
     */
    public DataSynchronizer<T, K> syncFrom(final List<T> syncFrom) {
        this.syncFrom = syncFrom;
        return this;
    }

    /**
     * Función para convertir un dato en su clave para realizar el emparejamiento con los datos de
     * sincronización.
     * @param keyMatcher Función para obtener clave de emparejamiento
     * @return DataSynchronizer actual
     */
    public DataSynchronizer<T, K> matchedBy(final Function<T, K> keyMatcher) {
        this.keyMatcher = keyMatcher;
        return this;
    }

    /**
     * Establece la función para evaluar la condición de creación de un dato nuevo.
     *
     * <p>
     * Por defecto la condición de creación es: No existe en los datos actuales pero sí en los datos de
     * sincronización.
     * @param conditionForCreate Función de cálculo de condición de creación.
     * @return DataSynchronizer actual
     */
    public DataSynchronizer<T, K> isCreateWhenPassed(BiPredicate<T, T> conditionForCreate) {
        this.isCreate = match -> conditionForCreate.test(match.getLeftValue(), match.getRightValue());
        return this;
    }

    /**
     * Establece la función de trasnformación de los datos de sincronización que se debe aplicar antes
     * de crear un nuevo dato. Permite adaptar/completar los datos obtenidos en la sincronización.
     *
     * <p>
     * Por defecto no se aplica ninguna trasnformación.
     * @param transformCreation Función de transformación. Operación unitaria que recibe un dato
     *        obtenido de la sincronización y debe devolver un dato (del mismo tipo) con los valores
     *        trasnformados.
     * @return DataSynchronizer actual
     */
    public DataSynchronizer<T, K> beforeCreateApply(UnaryOperator<T> transformCreation) {
        this.transformCreation = transformCreation;
        return this;
    }

    /**
     * Establece la acción a realizar sobre la lista de datos sincronizados que se hayan resuelto como
     * creación.
     *
     * <p>
     * WARNING: Se debe tener cuidado si se utiliza en conjunto con onSingleCreationApply.
     * @param bulkCreationAction Consumidor de los datos sincronizados como lista.
     * @return DataSynchronizer actual
     */
    public DataSynchronizer<T, K> forBulkCreationApply(Consumer<List<T>> bulkCreationAction) {
        this.bulkCreationAction = bulkCreationAction;
        return this;
    }

    /**
     * Establece la acción a realizar sobre cada dato sincronizado que se haya resuelto como creación.
     *
     * <p>
     * WARNING: Se debe tener cuidado si se utiliza en conjunto con forBulkCreationApply.
     * @param onSingleCreationAction Consumidor para cada dato sincronizado.
     * @return DataSynchronizer actual
     */
    public DataSynchronizer<T, K> onSingleCreationApply(UnaryOperator<T> onSingleCreationAction) {
        this.onSingleCreationAction = onSingleCreationAction;
        return this;
    }

    public DataSynchronizer<T, K> isUpdateWhenPassed(BiPredicate<T, T> conditionForUpdate) {
        this.isUpdate = match -> conditionForUpdate.test(match.getLeftValue(), match.getRightValue());
        return this;
    }

    public DataSynchronizer<T, K> beforeUpdateApply(BinaryOperator<T> transformUpdate) {
        this.transformUpdate = m -> transformUpdate.apply(m.getLeftValue(), m.getRightValue());
        return this;
    }

    public DataSynchronizer<T, K> forBulkUpdateApply(Consumer<List<T>> bulkUpdateAction) {
        this.bulkUpdateAction = bulkUpdateAction;
        return this;
    }

    public DataSynchronizer<T, K> onSingleUpdateApply(UnaryOperator<T> onSingleUpdateAction) {
        this.onSingleUpdateAction = onSingleUpdateAction;
        return this;
    }

    public DataSynchronizer<T, K> isDeleteWhenPassed(BiPredicate<T, T> conditionForDelete) {
        this.isDelete = match -> conditionForDelete.test(match.getLeftValue(), match.getRightValue());
        return this;
    }

    public DataSynchronizer<T, K> beforeDeleteApply(UnaryOperator<T> transformDeletion) {
        this.transformDeletion = transformDeletion;
        return this;
    }

    public DataSynchronizer<T, K> forBulkDeletionApply(Consumer<List<T>> bulkDeletionAction) {
        this.bulkDeletionAction = bulkDeletionAction;
        return this;
    }

    public DataSynchronizer<T, K> onSingleDeletionApply(UnaryOperator<T> onSingleDeletionAction) {
        this.onSingleDeletionAction = onSingleDeletionAction;
        return this;
    }

    public DataSynchronizer<T, K> isEqualWhenPassed(BiPredicate<T, T> conditionForEqual) {
        this.isEqual = match -> conditionForEqual.test(match.getLeftValue(), match.getRightValue());
        return this;
    }

    public DataSynchronizer<T, K> beforeEqualApply(BinaryOperator<T> transformEqual) {
        this.transformEqual = m -> transformEqual.apply(m.getLeftValue(), m.getRightValue());
        return this;
    }

    public DataSynchronizer<T, K> forBulkEqualApply(Consumer<List<T>> bulkEqualAction) {
        this.bulkEqualAction = bulkEqualAction;
        return this;
    }

    public DataSynchronizer<T, K> onSingleEqualApply(UnaryOperator<T> onSingleEqualAction) {
        this.onSingleEqualAction = onSingleEqualAction;
        return this;
    }

    public DataSyncResult execute() {

        DataSyncResult dataSyncResult = new DataSyncResult();

        final DataSyncMatches<K, T> matches = new DataSyncMatcher<T>().left(withData).right(syncFrom).match(keyMatcher);

        if (isMatchOptionPresent(DataSyncMatchOptions.MATCH_CREATE)) {

            final List<T> created = matches.stream()
                .filter(isCreate)
                .map(DataSyncMatch::getRightValue)
                .map(transformCreation)
                .map(onSingleCreationAction)
                .collect(Collectors.toList());

            bulkCreationAction.accept(created);
            dataSyncResult.setCreatedResourcesCount(created.size());
        }

        if (isMatchOptionPresent(DataSyncMatchOptions.MATCH_DELETE)) {

            final List<T> deleted = matches.stream()
                .filter(isDelete)
                .map(DataSyncMatch::getLeftValue)
                .map(transformDeletion)
                .map(onSingleDeletionAction)
                .collect(Collectors.toList());

            bulkDeletionAction.accept(deleted);
            dataSyncResult.setDeletedResourcesCount(deleted.size());
        }

        if (isMatchOptionPresent(DataSyncMatchOptions.MATCH_UPDATE)) {

            final List<T> updated = matches.stream()
                .filter(isCreate.negate().and(isDelete.negate()).and(isUpdate))
                .map(transformUpdate)
                .map(onSingleUpdateAction)
                .collect(Collectors.toList());

            bulkUpdateAction.accept(updated);
            dataSyncResult.setUpdatedResourcesCount(updated.size());
        }

        if (isMatchOptionPresent(DataSyncMatchOptions.MATCH_EQUAL)) {

            final List<T> equal = matches.stream()
                .filter(isCreate.negate().and(isDelete.negate()).and(isUpdate.negate()).and(isEqual))
                .map(transformEqual)
                .map(onSingleEqualAction)
                .collect(Collectors.toList());

            bulkEqualAction.accept(equal);
            dataSyncResult.setEqualResourcesCount(equal.size());
        }

        return dataSyncResult;
    }

    private boolean isMatchOptionPresent(DataSyncMatchOptions option) {
        return this.matchOptions.contains(option);
    }

}
