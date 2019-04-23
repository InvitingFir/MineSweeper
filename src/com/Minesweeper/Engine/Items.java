package com.Minesweeper.Engine;

public class Items {
    private boolean bomb;
    private int number;
    private boolean isFlagged;
    private boolean gotChecked = false;
    private Items [][] Items = new Items[10][10];
    private int X = (int)(Math.random()*9);
    private int Y = (int)(Math.random()*9);

    void ItemGenerator() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Items[i][j] = new Items();
            }
        }
    }

    void BombSet(){
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Items[i][j].bomb = false;
                Items[i][j].number = 0;
                Items[i][j].gotChecked = false;
            }
        }
            for (int i = 0; i < 20; i++) {      //задаем координаты бомб
                while (Items[X][Y].bomb == true) {
                    X = (int) (Math.random() * 9);
                    Y = (int) (Math.random() * 9);
                }
                Items[X][Y].bomb = true;
                ItemInteract(X, Y);
                }
        }



    public void ItemInteract(int X,int Y ) {
        if (X == 0 && Y == 0) {
            Ritem(X, Y);
            DRitem(X, Y);
            Ditem(X, Y);
        } else if (X == 9 && Y == 0) {
            Litem(X, Y);
            DLitem(X, Y);
            Ditem(X, Y);
        } else if (X == 9 && Y == 9) {
            Uitem(X, Y);
            ULitem(X, Y);
            Litem(X, Y);
        } else if (X == 0 && Y == 9) {
            Uitem(X, Y);
            URitem(X, Y);
            Ritem(X, Y);
        } else if (X == 0) {
            Uitem(X, Y);
            URitem(X, Y);
            Ritem(X, Y);
            DRitem(X, Y);
            Ditem(X, Y);
        } else if (Y == 0) {
            Litem(X, Y);
            DLitem(X, Y);
            Ditem(X, Y);
            DRitem(X, Y);
            Ritem(X, Y);
        } else if (X == 9) {
            Uitem(X, Y);
            ULitem(X, Y);
            Litem(X, Y);
            DLitem(X, Y);
            Ditem(X, Y);
        } else if (Y == 9) {
            Litem(X, Y);
            ULitem(X, Y);
            Uitem(X, Y);
            URitem(X, Y);
            Ritem(X, Y);
        } else {
            Uitem(X, Y);
            URitem(X, Y);
            Ritem(X, Y);
            DRitem(X, Y);
            Ditem(X, Y);
            DLitem(X, Y);
            Litem(X, Y);
            ULitem(X, Y);
        }
    }


    private void Uitem(int X, int Y){
        Y--;
            Items[X][Y].number++;
    }
    private void URitem(int X, int Y){
        Y--;
        X++;
            Items[X][Y].number++;
    }
    private void Ritem(int X, int Y){
        X++;
            Items[X][Y].number++;
    }
    private void DRitem(int X, int Y){
        X++;
        Y++;
            Items[X][Y].number++;
    }
    private void Ditem(int X, int Y){
        Y++;
            Items[X][Y].number++;
    }
    private void DLitem(int X, int Y){
        Y++;
        X--;
            Items[X][Y].number++;
    }
    private void Litem(int X, int Y){
        X--;
            Items[X][Y].number++;
    }
    private void ULitem(int X, int Y){
        Y--;
        X--;
            Items[X][Y].number++;
    }


    int ItemsNumberGetter(int X, int Y){
        int number = Items[X][Y].number;
        return number;
    }
    boolean ItemsBombGetter(int X, int Y){
        boolean bomb = Items[X][Y].bomb;
        return bomb;
    }

    void FlagSetter(int X, int Y, boolean flag){
        Items[X][Y].isFlagged = flag;
    }

    boolean FlagGetter(int X, int Y){
        boolean flag = Items[X][Y].isFlagged;
        return flag;
    }
    void GotCheckedSetter(int X, int Y){
        Items[X][Y].gotChecked = true;
    }
    boolean GotCheckedGetter(int X, int Y){
        return Items[X][Y].gotChecked;
    }
}
