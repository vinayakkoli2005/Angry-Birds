package game.com;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;

import java.util.LinkedList;
import java.util.Queue;

import static game.com.Level.birds;
import static game.com.Level1Screen.blocks;
import static game.com.Level1Screen.pigs;


public class CollisionHandler implements ContactListener {
    public static Queue<Runnable> destructionQueue = new LinkedList<>();
    private Sling_Shot slingshot;
    private Main main;
    Level level;

    public CollisionHandler(Sling_Shot slingshot, Main main,Level level) {
        this.level = level;
        this.slingshot = slingshot;
        this.main = main;
    }

    public static void processPendingTasks() {
        while (!destructionQueue.isEmpty()) {
            Runnable task = destructionQueue.poll();
            task.run();
        }
    }

    @Override
    public void beginContact(Contact contact) {
        System.out.println("Begin contact");

        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        Object userDataA = fixtureA.getBody().getUserData();
        Object userDataB = fixtureB.getBody().getUserData();
        if(Level1Screen.birds.isEmpty() && !Level1Screen.pigs.isEmpty()) {
            if(level instanceof Level1Screen) {
                ((Level1Screen) level).dispose();
            }
            if(level instanceof Level2Screen) {
                ((Level2Screen) level).dispose();
            }
            if(level instanceof Level3Screen) {
                ((Level3Screen) level).dispose();
            }
            main.setScreen(new Win_screen(main));

        } else if (Level1Screen.pigs.isEmpty()) {

            main.setScreen(new Win_screen(main));

        }


        if (userDataA instanceof Bird && userDataB instanceof Block) {
            handleBirdBlockCollision((Bird) userDataA, (Block) userDataB);

        } else if (userDataA instanceof Block && userDataB instanceof Bird) {
            handleBirdBlockCollision((Bird) userDataB, (Block) userDataA);

        } else if (userDataA instanceof Bird && userDataB instanceof Pig) {
            handleBirdPigCollision((Bird) userDataA, (Pig) userDataB);

        } else if (userDataA instanceof Pig && userDataB instanceof Bird) {
            handleBirdPigCollision((Bird) userDataB, (Pig) userDataA);

        } else if (userDataA instanceof Pig && userDataB instanceof Block) {
            handlePig_Block_Collision((Pig) userDataA, (Block) userDataB);

        } else if (userDataA instanceof Block && userDataB instanceof Pig) {
            handlePig_Block_Collision((Pig) userDataB, (Block) userDataA);
        }

    }


    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }

    private void handlePig_Block_Collision(Pig pig, Block block) {
        System.out.println("pig block collision");
        pig.health -= 1;

        if (pig.health <= 0) {
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    destructionQueue.add(() -> {
                        Level.world.destroyBody(pig.body);
                        pigs.remove(pig);});
                }
            }, 0.1f);

            for (int i = 0; i < pigs.size(); i++) {
                if (pigs.get(i).getBody() == pig.body) {
                    pigs.remove(i);
                    break;
                }
            }
        }

        block.health -= 1;

        if (block.health <= 0) {

            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    destructionQueue.add(() -> {
                        Level.world.destroyBody(block.body);
                        blocks.remove(block);
                    });
                }
            }, 0.1f);

        }
    }


    private void handleBirdBlockCollision(Bird bird, Block block) {

        System.out.println("Collision detected between Bird and Block");

        block.reduceHealth(bird);
        Block_Bird_Collision(block, bird);
        BirdCollision(bird);

    }

    private void Block_Bird_Collision(Block B, Bird Bird) {

        B.health -= Bird.getDamage();

        if (B.health <= 0) {
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    destructionQueue.add(() -> {
                        Level.world.destroyBody(B.body);
                        blocks.remove(B);
                    });
                }
            }, 0.1f);

        }
    }

    private void handleBirdPigCollision(Bird bird, Pig pig) {
        System.out.println("Bird Pig collision detected"
        );
        pig.health -= bird.getDamage();

        if (pig.health <= 0) {
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    destructionQueue.add(() -> {
                        Level.world.destroyBody(pig.body);
                        pigs.remove(pig);
                    });
                }
            }, 0.1f);
        }
    }


    private void BirdCollision(Bird B) {

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if (B.level instanceof Level1Screen) {
                    Level1Screen level1 = (Level1Screen) B.level;


                    for (int i = 0; i < birds.size(); i++) {
                        if (birds.get(i).getBody() == B.body) {
                            birds.remove(i);
                            break;
                        }
                    }

                    destructionQueue.add(() -> {
                        Level.world.destroyBody(B.body);
                        birds.remove(B);
                    });

                    if (birds.isEmpty()) {
                        level.setlose();
                        level1.getmultiplexer().removeProcessor(1);


                    } else if (Level1Screen.pigs.isEmpty()) {
                        Level1Screen.setwin();
                    }else {
                        slingshot.selectedBird();
                    }
                }
                if (B.level instanceof Level2Screen) {
                    Level2Screen level2 = (Level2Screen) B.level;


                    for (int i = 0; i < birds.size(); i++) {
                        if (birds.get(i).getBody() == B.body) {
                            birds.remove(i);
                            break;
                        }
                    }

                    destructionQueue.add(() -> Level.world.destroyBody(B.body));


                    if (birds.isEmpty()) {
                        level.setlose();
                        level2.getmultiplexer().removeProcessor(1);

                    } else if (Level2Screen.pigs.isEmpty()) {

                        Level2Screen.setwin();
                    }else {
                        slingshot.selectedBird();
                    }
                }
                if (B.level instanceof Level3Screen) {

                    Level3Screen level3 = (Level3Screen) B.level;


                    for (int i = 0; i < birds.size(); i++) {
                        if (birds.get(i).getBody() == B.body) {
                            birds.remove(i);
                            break;
                        }
                    }

                    destructionQueue.add(() -> Level.world.destroyBody(B.body));


                    if (birds.isEmpty()) {
                        level.setlose();
                        level3.getmultiplexer().removeProcessor(1);

                    } else if (Level3Screen.pigs.isEmpty()) {
                        Level3Screen.setwin();
                    }else {
                        slingshot.selectedBird();
                    }
                }


            }


        }, 2f);
    }
}
