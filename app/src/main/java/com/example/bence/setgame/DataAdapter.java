package com.example.bence.setgame;

import android.content.Context;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

import static android.graphics.Color.*;

/**
 * Created by Bence on 2015.12.25..
 */
public class DataAdapter extends BaseAdapter {
    private StartGame startGame;
    private Context context;
    private List<Card> cardList;

    DataAdapter (Context ctx, StartGame game)
    {
        context = ctx;
        startGame = game;
        cardList = new ArrayList<Card>();
    }

    @Override
    public int getCount() { return cardList.size(); }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Button button;

        if (convertView == null) {
            button = new Button(context);
            ViewGroup.LayoutParams layout = new ViewGroup.LayoutParams(250, 150);
            button.setLayoutParams(layout);
        }
        else button = (Button) convertView;

        int pictureID = button.getResources().getIdentifier(cardList.get(position).getPictureRes(), "id", "com.example.bence.setgame");
        button.setBackgroundResource(pictureID);
        button.setAlpha(1.0f);
        button.setTag(cardList.get(position));
        button.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v){
                  Card card = cardList.get(position);
                  if(!card.getIsClickedOn()) {
                      if (startGame.getClickedCount() == 3) {
                          Toast.makeText(startGame, "Select exactly 3 cards", Toast.LENGTH_SHORT).show();
                          return;
                      }
                  }

                  card.clicked();

                  if(card.getIsClickedOn())
                  {
                      startGame.cardClickedOn(card);
                      button.setAlpha(0.5f);
                  }
                  else
                  {
                      startGame.cardClickedOff(card);
                      int pictureID = button.getResources().getIdentifier(card.getPictureRes(), "id", "com.example.bence.setgame");
                      button.setBackgroundResource(pictureID);
                      button.setAlpha(1.0f);
                  }

              }
          }
        );

        return button;
    }

    public void addCard(Card card)
    {
        cardList.add(card);
    }

    public void removeCard (Card card) { cardList.remove(card); }

    public int getNumberOfCards () { return cardList.size(); }

    public List<Card> getCardList () { return cardList; }
}
