# Hi!
## Homework 3
- Work in **groups**  
- Deadline: **4 may**
- Send **single pull request** per group)

## What we have
- You already have a **game model**, implemented in **HW2**.  
- In lecture 8 we discussed how to use websocket for client-server communication.  
We use **Broker.java** as API for sending and receiving messages.
## Task
Implement client-server communication protocol and connect it with model.

### 1. Add **time model** to your **game model**
#### Time model
Time can be modelled differently in game. We will use the time model where time is emulated by constant-time **frames** of duration **FRAME_TIME**;  
  
In our model every game object changing in time, must implement Tickable.
```java
public interface Tickable {
    void tick(long time);
}
```
All the **game objects** are ticked by **GameSession**. **GameSession** is ticked by **Ticker** (see **lecture10**).  
  
The **Ticker** implementation provided in **source directory** implement awaiting until **FRAME_TIME** ends if processing finished earlier. However, if the server experiencing overload, processing time can exceed **FRAME_TIME**.  
  
Implement ticking on all your Tickable game objects

### 2. Connect network to you model
After server establishes **Session** with client, bi-directional network communication begins. Implement processing of **MOVE** and **PLANT_BOMB** messages and sending **POSSESS** and **REPLICA** messages to frontend.

##### Messages
###### MOVE
client -> server
```json
{
  "topic":"MOVE",
  "data":
    {
      "direction":"UP"
    }
}
```
direction values: UP/DOWN/RIGHT/LEFT
Only one movement message is executed per tick 

###### PLANT_BOMB
Plants bomb in the position of current player
client -> server
```json
{
   "topic": "PLANT_BOMB",
   "data": {}
}
```

**POSSESS**
Is a message you need to send to client at the beginning of game (before any Replica).
Reasoon to have possessed pawn is to play personal animations, sounds and other events.
```json
{
  "topic": "POSSESS",
  "data": <id of pawn to possess>
}
```

**REPLICA**
Sends server state to clients in the end of every tick

```json
{
  "topic": "REPLICA",
  "data": [ obj1, obj2, ... ]
}
```
Objects could be:
```json
{
  "type": "Pawn" or "Bomb" or "Wall" or "Wood" or "Fire"
  "id" : <id of object>
  "position" : {
    "x" : <number>
    "y" : <number>
  }
}
```
By default frontend produce all coordinates transformation.
  
**Note:** you should work in dedicated branch of your choice. In this branch you will implement the whole game
