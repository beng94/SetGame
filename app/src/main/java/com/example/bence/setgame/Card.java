package com.example.bence.setgame;

import org.w3c.dom.Text;

/**.
 * Created by Bence on 2015.12.26..
 */
public class Card {
    private Color color;
    private Shape shape;
    private Number number;
    private Texture texture;
    private boolean isClickedOn;
    private String pictureRes;

    public enum Color { RED, GREEN, PINK }
    public enum Shape { DIAMOND, PILL, WORM }
    public enum Number { ONE, TWO, THREE }
    public enum Texture { EMPTY, FILLED, STRIPED }

    public Card (Color color, Shape shape, Number number, Texture texture)
    {
        this.color = color;
        this.shape = shape;
        this.number = number;
        this.texture = texture;
        this.pictureRes = setPictureRes();
        this.isClickedOn = false;
    }

    private static <E extends Enum<E>> int enumToInt (E e)
    {
        return e.ordinal();
    }

    private String  setPictureRes ()
    {
        int id = enumToInt(color) +
                 enumToInt(shape) * 3 +
                 enumToInt(number) * 9 +
                 enumToInt(texture) * 27 + 1;

        return "drawable/card_" + id;
    }

    public Color getColor() { return color; }

    public Shape getShape () { return shape; }

    public Number getNumber () { return number; }

    public Texture getTexture () { return  texture; }

    public boolean getIsClickedOn () { return isClickedOn; }

    public String getPictureRes () { return pictureRes; }

    public void clicked () { isClickedOn = !isClickedOn; }
}
