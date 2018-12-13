package com.raonstudio.foxforest

data class GameRoom(
        val roomId: String,
        val name: String,
        val state: GameState
) {
    constructor(name: String) : this("room${System.currentTimeMillis()}", name, GameState.WAITING)
}

enum class GameState{
    WAITING, READY, PLAYING
}