package com.mikhailgrigorev.game.databases

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.mikhailgrigorev.game.core.ecs.Component
import com.mikhailgrigorev.game.core.ecs.Components.BitmapComponent
import com.mikhailgrigorev.game.core.ecs.Components.HealthComponent
import com.mikhailgrigorev.game.entities.Enemy
import com.mikhailgrigorev.game.entities.Player

class DBHelperFunctions {

    private fun isEnemyExists(id: Int, context: Context): Boolean {
        var c: Cursor? = null
        val db = EnemyDBHelper(context).readableDatabase
        return try {
            val query = "select * from enemies where _id = $id"
            c = db.rawQuery(query, null)
            c.moveToFirst()
        } finally {
            c?.close()
            db?.close()
        }
    }

    // "FUNCTIONS FOR TEST" BLOCK
    //-------------------------------------------------------
    //-------------------------------------------------------
    fun restorePlayerHealth(context: Context, player: Player){
        val playerHealthComponent = player.getComponent(HealthComponent::class.java)
        playerHealthComponent!!.upgrade(HealthComponent.HealthUpgrader(
            playerHealthComponent.maxHealthPoints - playerHealthComponent.healthPoints,
            0
        ) as Component.ComponentUpgrader<Component>)

        val dbHelper = PlayerDBHelper(context)
        val database = dbHelper.writableDatabase
        val playerId = player.getComponent(BitmapComponent::class.java)!!._id
        val contentValues = ContentValues()

        contentValues.put(EnemyDBHelper.HEALTH, playerHealthComponent.healthPoints)
        database.update(PlayerDBHelper.TABLE_PLAYER, contentValues, "_id = $playerId", null)
    }
    //-------------------------------------------------------
    //-------------------------------------------------------

    fun spawnEnemy(context: Context, enemy: List<String>){
        val dbHelper = EnemyDBHelper(context)
        val database = dbHelper.writableDatabase
        val contentValues = ContentValues()

         contentValues.put(EnemyDBHelper.EnemyID, enemy[0].toInt())
         contentValues.put(EnemyDBHelper.multiple, enemy[1].toInt())
         contentValues.put(EnemyDBHelper.X, enemy[2].toInt())
         contentValues.put(EnemyDBHelper.Y, enemy[3].toInt())
         contentValues.put(EnemyDBHelper.SIZE, enemy[4].toInt())
         contentValues.put(EnemyDBHelper.ID, enemy[5].toInt())
         contentValues.put(EnemyDBHelper.HEALTH, enemy[6].toInt())
         contentValues.put(EnemyDBHelper.DMG, enemy[7].toInt())
         contentValues.put(EnemyDBHelper.AIR, enemy[8].toInt())
         contentValues.put(EnemyDBHelper.WATER, enemy[9].toInt())
         contentValues.put(EnemyDBHelper.EARTH, enemy[10].toInt())
         contentValues.put(EnemyDBHelper.FIRE, enemy[11].toInt())
         contentValues.put(EnemyDBHelper.CC, enemy[12].toInt())
         contentValues.put(EnemyDBHelper.CM, enemy[13].toInt())
         contentValues.put(EnemyDBHelper.DEFENCE, enemy[14].toInt())
         contentValues.put(EnemyDBHelper.AIR2, enemy[15].toInt())
         contentValues.put(EnemyDBHelper.WATER2, enemy[16].toInt())
         contentValues.put(EnemyDBHelper.EARTH2, enemy[17].toInt())
         contentValues.put(EnemyDBHelper.FIRE2, enemy[18].toInt())
         contentValues.put(EnemyDBHelper.Special, 0)

         if (!isEnemyExists(enemy[5].toInt(), context)) {
             database.insert(EnemyDBHelper.TABLE_ENEMIES, null, contentValues)
         }
    }

    fun setPlayerHealth(context: Context, player: Player){
        val dbHelper = PlayerDBHelper(context)
        val database = dbHelper.writableDatabase
        val playerId = player.getComponent(BitmapComponent::class.java)!!._id
        val contentValues = ContentValues()

        contentValues.put(EnemyDBHelper.HEALTH, player.getComponent(HealthComponent::class.java)!!.healthPoints)
        database.update(PlayerDBHelper.TABLE_PLAYER, contentValues, "_id = $playerId", null)
    }

    fun deleteEnemy(context: Context, enemy: Enemy){
        val dbHelper = EnemyDBHelper(context)
        val database = dbHelper.writableDatabase
        val enemyId = enemy.getComponent(BitmapComponent::class.java)!!._id

        database.delete(EnemyDBHelper.TABLE_ENEMIES, "_id = $enemyId", null)
    }
}