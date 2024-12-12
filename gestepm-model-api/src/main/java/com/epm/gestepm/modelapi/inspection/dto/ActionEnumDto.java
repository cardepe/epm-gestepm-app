package com.epm.gestepm.modelapi.inspection.dto;

import java.util.Map;

public enum ActionEnumDto {
    DIAGNOSIS,
    INTERVENTION,
    FOLLOWING;

    public static ActionEnumDto getNextAction(final ActionEnumDto lastAction) {
        final Map<ActionEnumDto, ActionEnumDto> actionFlow = Map.of(
                ActionEnumDto.DIAGNOSIS, ActionEnumDto.INTERVENTION,
                ActionEnumDto.INTERVENTION, ActionEnumDto.FOLLOWING,
                ActionEnumDto.FOLLOWING, ActionEnumDto.DIAGNOSIS
        );

        return actionFlow.getOrDefault(lastAction, ActionEnumDto.DIAGNOSIS);
    }
}
