package com.example.bence.setgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bence on 2015.12.24..
 */
public class StartGame extends Activity {
    private DataAdapter dataAdapter;
    private CardPool cardPool;
    private List<Card> clickedCards;
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        dataAdapter = new DataAdapter(this, this);
        cardPool = new CardPool();
        clickedCards = new ArrayList<Card>();

        initNewCardsButton();
        initHintButton();

        initCards();
        gridView = (GridView) findViewById(R.id.gridv_game);
        gridView.setAdapter(dataAdapter);
    }

    private void initCards ()
    {
        for(int i = 0; i < 12; i++)
        {
            dataAdapter.addCard(cardPool.getRandomCard());
        }
    }

    private void initNewCardsButton ()
    {
        Button button = (Button) findViewById(R.id.bt_game_new_card);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dataAdapter.getNumberOfCards() >= 21)
                {
                    Toast.makeText(StartGame.this, "There must be a set", Toast.LENGTH_SHORT).show();
                    return;
                }

                for (int i = 0; i < 3; i++) {
                    dataAdapter.addCard(cardPool.getRandomCard());
                }

                dataAdapter.notifyDataSetChanged();
            }
        });
    }

    private void addAnimationToButton (Button button)
    {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
        button.startAnimation(anim);
    }

    private List<Button> findButtonsByCard (List<Card> soughtCardsList)
    {
        List<Card> cardList = dataAdapter.getCardList();
        List<Button> buttonList = new ArrayList<Button>();

        for(int i = 0; i < soughtCardsList.size(); i++)
        {
            Card soughtCard = soughtCardsList.get(i);
            for(int j = 0; j < cardList.size(); j++)
            {
                if(soughtCard == cardList.get(j))
                {
                    Button soughtButton = (Button) gridView.getChildAt(j);
                    buttonList.add(soughtButton);
                    continue;
                }

                if(buttonList.size() == 3) break;
            }
        }

        return buttonList;
    }

    private void initHintButton ()
    {
        Button button = (Button) findViewById(R.id.bt_game_hint);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Card> setList = cardPool.findSet(dataAdapter.getCardList());

                if (setList.size() == 0) {
                    Toast.makeText(StartGame.this, "No set found", Toast.LENGTH_SHORT).show();
                } else {
                    List<Button> setButtonsList = findButtonsByCard(setList);
                    for(Button setButton: setButtonsList)
                    {
                        addAnimationToButton(setButton);
                    }
                    Toast.makeText(StartGame.this, "Set available", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void removeCardsFromSet ()
    {
        for (Card card: clickedCards)
        {
            dataAdapter.removeCard(card);
        }

        clickedCards.clear();

        dataAdapter.notifyDataSetChanged();
    }

    public void cardClickedOn (Card card)
    {
        clickedCards.add(card);

        if(clickedCards.size() == 3)
        {
            if(cardPool.isSet(clickedCards))
            {
                removeCardsFromSet();
                Toast.makeText(this, "Found a set", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void cardClickedOff (Card card) { clickedCards.remove(card); }

    public int getClickedCount () { return clickedCards.size(); }
}


