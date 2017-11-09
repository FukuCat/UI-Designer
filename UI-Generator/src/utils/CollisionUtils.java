package utils;

import jGame.model.game.GameActor;
import jGame.model.game.GameObject;
import jGame.model.math.Vector2f;

public class CollisionUtils {
    private CollisionUtils(){}
    public static boolean aabbCollision2D(GameActor a, GameActor b){
        if(Math.abs((a.getPosition().getX()+a.getScale().getX()/2) - (b.getPosition().getX()+b.getScale().getX()/2)) < a.getScale().getX()/2 + b.getScale().getX()/2)
        {
            if(Math.abs((a.getPosition().getY()+a.getScale().getY()/2) - (b.getPosition().getY()+b.getScale().getY()/2)) < a.getScale().getY()/2 + b.getScale().getY()/2)
            {
                return true;
            }
        }

        return false;
    }
    public static boolean pointCollision2D(GameActor a, Vector2f b){
        if(Math.abs(a.getPosition().getX() - b.getX()) < a.getScale().getX()/2)
        {
            if(Math.abs(a.getPosition().getY() - b.getY()) < a.getScale().getY()/2)
            {
                return true;
            }
        }
        return false;
    }
    public static boolean pointCollision2D(GameActor a, float pointX, float pointY){
        if(Math.abs(a.getPosition().getX() - pointX) < a.getScale().getX()/2)
        {
            if(Math.abs(a.getPosition().getY() - pointY) < a.getScale().getY()/2)
            {
                return true;
            }
        }
        return false;
    }
}
