/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo.lambdaexp;

/**
 *
 * @author kpadhikari File made after watching
 * https://www.youtube.com/watch?v=Pr-p5LNjS0c on What & Why do we need Lambda
 * Expressions in Java? | Tech Primers
 */
public class LambdaExpDemoGame {

    public static void main(String[] args) {

        //Before Java 8, we used to do the following
        //    which is the Imperative style (kp: inner class with single methods?)
        Game football = new Game() {
            public void play() {
                System.out.println("I'm playing Football.");
            }
        };

        Game cricket = new Game() {
            public void play() {
                System.out.println("I'm playing Cricket.");
            }
        };
        
        /*
        Following Lambda exp. was created automatically by clicking on the little
        yellow 'suggestion' bulb that asked whether I wanted to use the lambda 
        expression instead of my original code which was as follows:
        
        Game dandibiyo = new Game() {
            public void play() {
                System.out.println("I'm playing Dandibiyo.");
            }
        };

        When I clicked on it to confirm that I wanted to use the expression, it simply 
        removed 'new Game' and then the method declaration 'public void play()' along with
        it's enclusing curly braces. In fact, we can even get rid of the remaining pair of
        the curly braces that included the whole inner class i.e. the one coming after the
        'new Game()' and it's closing counterpart. (see below)
        */
        
        Game dandibiyo = () -> {
            System.out.println("I'm playing Dandibiyo.");
        };

        //Now the functional style introduced in the Java 8 
        //  i.e Lambda Expression
        Game footballLambda = () -> System.out.println("I'm Playing Football (LE)");
        //Series odiSeries = () -> System.out.println("I'm playing ODI Series (LE)");
        Series odiSeries = (arg) -> System.out.println("I'm playing" + arg + " (LET)");
        football.play();
        cricket.play();
        footballLambda.play();
        //odiSeries.play();
        odiSeries.play("ODI Series");
    }

    interface Game {

        void play();
    }

    interface Series {

        void play(String types);
    }
}
