package com.example.bence.setgame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Bence on 2015.12.26..
 */
public class CardPool {
    private List<Card> cardList;
    private Random randomGenerator;

    CardPool ()
    {
        cardList = new ArrayList<Card>();
        randomGenerator = new Random();

        for (Card.Color c: Card.Color.values())
        {
            for (Card.Shape s: Card.Shape.values())
            {
                for (Card.Number n: Card.Number.values())
                {
                    for (Card.Texture t: Card.Texture.values())
                    {
                        cardList.add(new Card(c, s, n, t));
                    }
                }
            }
        }
    }

    public Card getRandomCard ()
    {
        int index = randomGenerator.nextInt(cardList.size());
        return cardList.remove(index);
    }

    private static <E extends Enum<E>>
    boolean valuesSameOrDifferent(E a, E b, E c)
    {
        return (a == b && b == c) || (a != b && b != c && c != a);
    }

    public boolean isSet (List<Card> cardList)
    {
        Card a = cardList.get(0);
        Card b = cardList.get(1);
        Card c = cardList.get(2);

        return isSet(a, b, c);
    }

    public boolean isSet (Card a, Card b, Card c)
    {
        return  valuesSameOrDifferent(a.getColor(), b.getColor(), c.getColor()) &&
                valuesSameOrDifferent(a.getNumber(), b.getNumber(), c.getNumber()) &&
                valuesSameOrDifferent(a.getShape(), b.getShape(), c.getShape()) &&
                valuesSameOrDifferent(a.getTexture(), b.getTexture(), c.getTexture());
    }

    public List<Card> findSet (List<Card> cardList)
    {
        List<Card> setList = new ArrayList<Card>();

        int size = cardList.size();
        for(int i = 0; i < size; i++)
        {
            Card a = cardList.get(i);
            for(int j = i + 1; j < size; j++)
            {
                Card b = cardList.get(j);
                for(int k = j + 1; k < size; k++)
                {
                    if(isSet(a, b, cardList.get(k)))
                    {
                        setList.add(a);
                        setList.add(b);
                        setList.add(cardList.get(k));

                        return setList;
                    }
                }
            }
        }

        return setList;
    }
}
