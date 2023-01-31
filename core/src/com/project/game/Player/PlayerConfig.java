/*
* Structure that holds variable that may be
* configured for each player. Each variable
* respresents a cosmetic choice.
* 
* @author  Rameen Popal
* @since   2023-01-31
*/

package com.project.game.Player;

public class PlayerConfig {
    
    public short playerColorNumber;
    public short faceSkin;
    public short shirtSkin;
    public short hatSkin;

    public PlayerConfig() {
        playerColorNumber = 2;
        faceSkin = 5;
        shirtSkin = 2;
        hatSkin = 0;

        
    }

}
