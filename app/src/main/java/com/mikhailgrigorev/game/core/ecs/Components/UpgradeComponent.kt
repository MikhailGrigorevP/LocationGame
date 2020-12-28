package com.mikhailgrigorev.game.core.ecs.Components

import com.mikhailgrigorev.game.core.ecs.Component
import com.mikhailgrigorev.game.core.ecs.Entity

class UpgradeComponent : Component() {
    var upgraders = ArrayList<ComponentUpgrader<out Component>>()

    fun <_Component : Component> addUpgrader(upgrader: ComponentUpgrader<_Component>) {
        this.upgraders.add(upgrader)
    }

    fun upgrade(entity: Entity){
        for (upgrader in this.upgraders)
            entity.getComponent(upgrader.improvingComponent)?.upgrade(upgrader as ComponentUpgrader<Component>)
    }
}