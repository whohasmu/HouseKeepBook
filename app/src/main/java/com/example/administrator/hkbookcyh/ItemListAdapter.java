package com.example.administrator.hkbookcyh;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemListAdapter extends BaseAdapter{
    ArrayList<Item> items;


    public ItemListAdapter(ArrayList<Item> items) {
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        Holder holder = new Holder();
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
            holder.kind = convertView.findViewById(R.id.kind);
            holder.won = convertView.findViewById(R.id.won);
            holder.kind_IV = convertView.findViewById(R.id.kind_IV);
            holder.delete_Iv = convertView.findViewById(R.id.delete_Iv);
            holder.layout = convertView.findViewById(R.id.layout);
            convertView.setTag(holder);
        }else{
            holder = (Holder)convertView.getTag();
        }
        Item item = (Item)getItem(position);


        Integer tempWon = item.getWon();

        holder.won.setText(tempWon.toString());

        if (item.getKind() == 0) {
            holder.kind.setText(R.string.income);
            holder.kind_IV.setImageResource(R.drawable.shape_oval);
        } else {
            holder.kind.setText(R.string.outgoing);
            holder.kind_IV.setImageResource(R.drawable.shape_oval2);
        }

        final View aniView = holder.layout;

        holder.delete_Iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(parent.getContext());
                alertDialog.setTitle("경고");
                alertDialog.setMessage("정말 삭제하시겠습니까?");
                alertDialog.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Animation DeleteAnim = AnimationUtils.loadAnimation(parent.getContext(), R.anim.deleteitem);
                        DeleteAnim.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                DBManager dbManager = new DBManager(parent.getContext(), "items.db",null,1);
                                dbManager.deleteItem( ((Item) getItem(position)).getId() );
                                items.remove(position);
                                notifyDataSetChanged();
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });

                        aniView.startAnimation(DeleteAnim);

                    }
                });
                alertDialog.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertDialog.show();







            }
        });



        return convertView;
    }

    public  class Holder{

        ImageView kind_IV;
        TextView kind;
        TextView won;
        ImageView delete_Iv;
        LinearLayout layout;


    }
}
