package com.example.administrator.hkbookcyh;

public class ItemAddEvent {
    private Item item;

    public ItemAddEvent(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
