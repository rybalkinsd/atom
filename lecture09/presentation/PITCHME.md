#HSLIDE
# Java
lecture 9
## Game Server architecture.
## Client-server protocol

#HSLIDE
## Отметьтесь на портале
https://atom.mail.ru/

#HSLIDE
### get ready
```bash
> git fetch upstream
> git checkout -b lecture09 upstream/lecture09
```
Refresh gradle project


#HSLIDE
## Agenda
0. Game Server architecture
0. Time model
0. Game Server Hackaton
0. Client-server protocol

#HSLIDE
## Summary
0. **[Game Server architecture]**
0. Time model
0. Game Server Hackaton
0. Client-server protocol


#HSLIDE
## Game Server
Game Server is a separate application that do in cycle:
0. get's input from multiple clients
0. play game mechanics
0. send game state to clients (replica)
  
**On the next picture green components are already developed**


#HSLIDE
<img src="lecture09/presentation/assets/img/GameServerArchitecture.png" alt="exception" style="width: 800px;"/>


#HSLIDE
## Agenda
0. Game Server architecture
0. **[Time model]**
0. Game Server Hackaton
0. Client-server protocol

#HSLIDE
## Time model with variable tick time
> @see ru.atom.lecture09.tick.Ticker

In our model tick lasts until all messages from InoutQueue are handled  
So every time tick time is different  
**Advantages/Disadvantages?**

#HSLIDE
## Time model with variable tick time
So game mechanics should take **elapsed** as a parameter and use it internally  
We will discuss details when talk about game mechanics

#HSLIDE
## Agenda
0. Game Server architecture
0. Time model
0. **[Game Server Hackaton]**
0. Client-server protocol

#HSLIDE
## Create a branch for game server
Create the branch from branch lecture09, where you will develop the game server:
```bash
> git checkout -b game-server
```

#HSLIDE
## create a directory for game server
create a directory for game server  
Call it **game_server**  
In this directory you will create game server from scratch

#HSLIDE
## Game Server Hackaton
Implement the skeleton of the game server.
Everything on picture except:
- GameMechanics (Next Lectures)  
create stub for it
- ConnectionHandler (Homework)  
create stub for it
- Connect with MatchMaker  


#HSLIDE
## Agenda
0. Game Server architecture
0. Time model
0. Game Server Hackaton
0. **[Client-server protocol]**


#HSLIDE
## Client-server communication
Client and server talk via **websocket**  
We use **JSON** for messages  
### Client sends to server:
- MOVE
- PLANT_BOMB

### Server sends to client:
- REPLICA
- POSSESS

#HSLIDE
## MOVE
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

#HSLIDE
## PLANT_BOMB
client -> server
```json
{
   "topic": "PLANT_BOMB",
   "data": {}
}
```


#HSLIDE
## POSSESS
server -> client  
(returns player pawn id, once on the start of game)
```json
{
   "topic": "POSSESS",
   "data": 123
}
```


#HSLIDE
## REPLICA
```json
{
   "topic": "REPLICA",
   "data": "[{\"position\":{\"x\":16.0,\"y\":12.0},\"id\":16,\"type\":\"Wall\"},{\"position\":{\"x\":32.0,\"y\":32.0},\"id\":213,\"velocity\":0.05,\"maxBombs\":1,\"bombPower\":1,\"speedModifier\":1.0,\"type\":\"Pawn\"},{\"position\":{\"x\":32.0,\"y\":352.0},\"id\":214,\"velocity\":0.05,\"maxBombs\":1,\"bombPower\":1,\"speedModifier\":1.0,\"type\":\"Pawn\"}]"
}
```

#HSLIDE
## GAME_OVER
```json
{
   "topic": "GAME_OVER",
   "data": "You win!"
}
```

#HSLIDE
## Network implementation ideas
> @see ru.atom.lecture09.network


#HSLIDE
**Оставьте обратную связь**
(вам на почту придет анкета)  

**Это важно!**
