package com.example.candy_crush;

public class Parent {
    public void whoIs(){
        System.out.println();
    }
    public static void main(String [] args){
        Parent p = new Parent(){            //is not an instatiation
            public void whoIs(){
                System.out.println("hey!");
            }
        };
        p.whoIs();
    }

}
