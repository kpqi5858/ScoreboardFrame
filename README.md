# ScoreboardFrame
Awesome scoreboard plugin for Minecraft 1.5.2 or later

## Features
 - Pretty Customizable.
 - Like gif files, All scoreboard is based "Frame".
 - Using PlaceholderAPI to get Player's a lot of information.
 
## How to Edit

20
Title
This is message
Money : %vaulteco_balance%
%vaulteco_balance% is from PlaceHolderAPI.
Will show player's balance

&aColor is supported
;
Title
This is Second Frame
After 20 ticks(= 1sec)
This Scoreboard will shown
;
Title
";" Means "End of this frame"


First Line : Delay for each frames
Second Line : Title for first frame
....
Line that end of frame : ";" for divide each frames
Line that end of frame + 1 : That frame's Title
....



## ChangeLog

### 1.0

### 1.1
 - 1.5.2 Supoort (Divide for 1.5.2)
 - When join the server, scoreboard in the config will automatically executed
 
## 1.2
 - 1.5.2 Support (Combined)
 - Optimization (No longer 1 Thread = 1 Player)
