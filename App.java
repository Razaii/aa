import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
public class App extends Application {

    private Player player1; //The Paddle on the left
    private Player player2; //The Paddle on the right
    private obstacles obstacle1; //The ball
    private obstacles obstacle2; //The ball in multiBall and dodgeBall  
    private obstacles obstacle3; //The ball in multiBall and dodgeBall
    private Text scoreText; // Score display   
    private Text instructionText1; //Display Instructions    
    private Text instructionText2;    
    private Text instructionText3;
    private Text instructionText4;   
    private Text winnerText;
    private int player1Score = 0; // Player 1 score
    private int player2Score = 0; // Player 2 score
    private Scene introScene; // Main menu scene reference
    private AnimationTimer gameLoop;

    @Override
    public void start(Stage primaryStage) {
        // Main menu scene
        Pane root = new Pane();
        // Display game title and credits

        Text titleText = new Text("B.R.A.T. GAME");
        titleText.setFont(new Font("Arial", 75));
        titleText.setFill(Color.RED);
        titleText.setLayoutX(150);
        titleText.setLayoutY(200);

        Text creditsText = new Text("By: Ava Tsang and Rachel Balila");
        creditsText.setFont(new Font("Arial", 15));
        creditsText.setFill(Color.WHITE);
        creditsText.setLayoutX(275);
        creditsText.setLayoutY(230);

        Button button1 = new Button("NORMAL GAME");
        button1.setLayoutX(330);
        button1.setLayoutY(320);

        Button button2 = new Button("INSTRUCTIONS");
        button2.setLayoutX(330);
        button2.setLayoutY(270);

        Button button3 = new Button("RANDOM PADDLE SIZE");
        button3.setLayoutX(330);
        button3.setLayoutY(370);
       
        Button button4 = new Button ("RANDOM BALL SIZE");
        button4.setLayoutX(330);
        button4.setLayoutY(420);
       
        Button button5 = new Button ("MULTIBALL");
        button5.setLayoutX(330);
        button5.setLayoutY(470);
       
        Button button6 = new Button ("DODGEBALL");
        button6.setLayoutX(330);
        button6.setLayoutY(520);


button1.setOnAction(e -> {
    //Switch to normalGame when button is clicked
    normalGame(primaryStage);
});
        button3.setOnAction(e -> {
            // Switch to randPaddle scene when button is clicked
            randPaddle(primaryStage);
        });
        button4.setOnAction(e -> {
            //Switch to randBall scene when button is clicked         
            randBall(primaryStage);
        });
       
        button5.setOnAction(e -> {
            //Switch to multiBall scene when button is clicked     
            multiBall(primaryStage);
        });
       
        button6.setOnAction(e -> {
            //Switch to dodgeBall scene when button is clicked       
            dodgeBall(primaryStage);
        });

        button2.setOnAction(e -> {
        	//Switch to instruction when the button is clicked
        	instruction(primaryStage);
});

        root.getChildren().addAll(titleText, creditsText, button1, button2, button3, button4, button5, button6);
        root.setStyle("-fx-background-color: black;");

        introScene = new Scene(root, 800, 600); // Initialize main menu scene
        primaryStage.setTitle("B.R.A.T. Game");
        primaryStage.setScene(introScene);    
        primaryStage.show();

    }
    public void normalGame (Stage primaryStage) {

        // Create the randPaddle scene (new game mode)

        Pane normalGameRoot = new Pane();

        // Player 1 (left side)
        player1 = new Player(50, 250, 20, 100);
        player1.getRectangle().setFill(Color.WHITE);
        normalGameRoot.getChildren().add(player1.getRectangle());

        // Player 2 (right side)
        player2 = new Player(730, 250, 20, 100);
        player2.getRectangle().setFill(Color.WHITE);
        normalGameRoot.getChildren().add(player2.getRectangle());

        // Obstacle in the middle
        obstacle1 = new obstacles(380, 280); // Center of the screen
        normalGameRoot.getChildren().add(obstacle1.getRectangle());

        // Score Text at the top center of the screen
        scoreText = new Text("Player 1: " + player1Score + " | Player 2: " + player2Score);
        scoreText.setFont(new Font("Arial", 20));
        scoreText.setFill(Color.WHITE);
        scoreText.setLayoutX(250); // Centered horizontally
        scoreText.setLayoutY(30); // Positioned near the top
        normalGameRoot.getChildren().add(scoreText);

        // Create the randPaddle scene
        Scene normalGameScene = new Scene(normalGameRoot, 800, 600);
        normalGameRoot.setStyle("-fx-background-color: black;");

        // Handle key presses for player movement
        normalGameScene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.W) {
                player1.setDirection(0, -1); // Move player 1 up

            } else if (e.getCode() == KeyCode.S) {
                player1.setDirection(0, 1); // Move player 1 down

            } else if (e.getCode() == KeyCode.UP) {
                player2.setDirection(0, -1); // Move player 2 up

            } else if (e.getCode() == KeyCode.DOWN) {
                player2.setDirection(0, 1); // Move player 2 down

            }

        });

        normalGameScene.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.W || e.getCode() == KeyCode.S) {
                player1.setDirection(0, 0);  // Stop player 1 movement

            }
            if (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.DOWN) {
                player2.setDirection(0, 0);  // Stop player 2 movement
            }
        });

        // Start the game loop

         gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
            
                player1.update();
                player2.update();
                obstacle1.update();

                // Check for collision with top and bottom of the screen

                if (obstacle1.getRectangle().getY() <= 0 ||
                    obstacle1.getRectangle().getY() >= 600 - obstacle1.getRectangle().getHeight()) {
                	obstacle1.setDy(-obstacle1.getDy()); // Reverse direction      
                }
                // Check for collision with left and right edges
                if (obstacle1.getRectangle().getX() == 0) {
                    // Player 2 scores a point if the obstacle hits the left wall
                    player2Score++;
                    if (player1Score == 5) {
                        gameLoop.stop(); // Stop the game loop
                        winner(primaryStage); // Show winner screen
                    } else if (player2Score == 5) {
                        gameLoop.stop(); // Stop the game loop
                        winner(primaryStage); // Show winner screen
                    } else {
                        stopGame(primaryStage); // Go to main menu if no winner
                    }
                    
                }
                if (obstacle1.getRectangle().getX() == 800 - obstacle1.getRectangle().getWidth()) {
                    // Player 1 scores a point if the obstacle hits the right wall
                    player1Score++;
                    if (player1Score == 5) {
                        gameLoop.stop(); // Stop the game loop
                        winner(primaryStage); // Show winner screen
                    } else if (player2Score == 5) {
                        gameLoop.stop(); // Stop the game loop
                        winner(primaryStage); // Show winner screen
                    } else {
                        stopGame(primaryStage); // Go to main menu if no winner
                    }
                }
                // Check for collision with paddles
                if (player1.Colliding(obstacle1) || player2.Colliding(obstacle1)) {
                	obstacle1.setDx(-obstacle1.getDx()); // Reverse horizontal direction

                }

            }

        };

        gameLoop.start();

        // Switch to randPaddle scene

        primaryStage.setTitle("NORMAL GAME");
        primaryStage.setScene(normalGameScene);
        primaryStage.show();

    }

//Random Size Paddle
    public void randPaddle(Stage primaryStage) {
        // Create the randPaddle scene (new game mode)

        Pane randPaddleRoot = new Pane();
int padSize = (int) (Math.random()*400)+75;


        // Player 1 (left side)

        player1 = new Player(50, padSize, 20, 300-(padSize/2));
        player1.getRectangle().setFill(Color.WHITE);
        randPaddleRoot.getChildren().add(player1.getRectangle());

        // Player 2 (right side)

        player2 = new Player(730, padSize, 20, 300-(padSize/2));
        player2.getRectangle().setFill(Color.WHITE);
        randPaddleRoot.getChildren().add(player2.getRectangle());

        // Obstacle in the middle
        obstacle1 = new obstacles(380, 280); // Center of the screen
        randPaddleRoot.getChildren().add(obstacle1.getRectangle());

        // Score Text at the top center of the screen
        scoreText = new Text("Player 1: " + player1Score + " | Player 2: " + player2Score);
        scoreText.setFont(new Font("Arial", 20));
        scoreText.setFill(Color.WHITE);
        scoreText.setLayoutX(250); // Centered horizontally
        scoreText.setLayoutY(30); // Positioned near the top
        randPaddleRoot.getChildren().add(scoreText);

        // Create the randPaddle scene
        Scene randPaddleScene = new Scene(randPaddleRoot, 800, 600);
        randPaddleRoot.setStyle("-fx-background-color: black;");

        // Handle key presses for player movement
        randPaddleScene.setOnKeyPressed(e -> {

            if (e.getCode() == KeyCode.W) {
                player1.setDirection(0, -1); // Move player 1 up
            } else if (e.getCode() == KeyCode.S) {
                player1.setDirection(0, 1); // Move player 1 down
            } else if (e.getCode() == KeyCode.UP) {
                player2.setDirection(0, -1); // Move player 2 up
            } else if (e.getCode() == KeyCode.DOWN) {
               player2.setDirection(0, 1); // Move player 2 down

            }

        });

        randPaddleScene.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.W || e.getCode() == KeyCode.S) {
                player1.setDirection(0, 0);  // Stop player 1 movement

            }
            if (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.DOWN) {
                player2.setDirection(0, 0);  // Stop player 2 movement

            }

        });

        // Start the game loop
         gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                player1.update();
                player2.update();
                obstacle1.update();
                // Check for collision with top and bottom of the screen

                if (obstacle1.getRectangle().getY() <= 0 ||
                    obstacle1.getRectangle().getY() >= 600 - obstacle1.getRectangle().getHeight()) {
                    obstacle1.setDy(-obstacle1.getDy()); // Reverse direction
                }

                // Check for collision with left and right edges
                if (obstacle1.getRectangle().getX() == 0) {
                    // Player 2 scores a point if the obstacle hits the left wall
                    player2Score++;
                    if (player1Score == 5) {
                        gameLoop.stop(); // Stop the game loop
                        winner(primaryStage); // Show winner screen
                    } else if (player2Score == 5) {
                        gameLoop.stop(); // Stop the game loop
                        winner(primaryStage); // Show winner screen
                    } else {
                        stopGame(primaryStage); // Go to main menu if no winner
                    }
                }
                if (obstacle1.getRectangle().getX() == 800 - obstacle1.getRectangle().getWidth()) {
                    // Player 1 scores a point if the obstacle hits the right wall
                    player1Score++;
                    if (player1Score == 5) {
                        gameLoop.stop(); // Stop the game loop
                        winner(primaryStage); // Show winner screen
                    } else if (player2Score == 5) {
                        gameLoop.stop(); // Stop the game loop
                        winner(primaryStage); // Show winner screen
                    } else {
                        stopGame(primaryStage); // Go to main menu if no winner
                    }
                }
                // Check for collision with paddles
                if (player1.Colliding(obstacle1) || player2.Colliding(obstacle1)) {
                    obstacle1.setDx(-obstacle1.getDx()); // Reverse horizontal direction
                }
            }
        };

        gameLoop.start();

        // Switch to randPaddle scene

        primaryStage.setTitle("RANDOM PADDLE SIZE");

        primaryStage.setScene(randPaddleScene);

        primaryStage.show();

    }

    public void randBall(Stage primaryStage) {
        // Create the randBall scene (new game mode)
        Pane randBallRoot = new Pane();

        // Randomize the obstacle's size (square)
        double obstacleSize = (Math.random() * 100) + 20;  // Random size between 20 and 120

        // Player 1 (left side)
        player1 = new Player(50, 250, 20, 100);
        player1.getRectangle().setFill(Color.WHITE);
        randBallRoot.getChildren().add(player1.getRectangle());

        // Player 2 (right side)
        player2 = new Player(730, 250, 20, 100);
        player2.getRectangle().setFill(Color.WHITE);
        randBallRoot.getChildren().add(player2.getRectangle());

        // Obstacle in the middle with random size (square)
        obstacle1 = new obstacles(380, 280);  // Center of the screen
        obstacle1.setobstaclesize(obstacleSize, obstacleSize);  // Apply the random size (same for width and height)
        randBallRoot.getChildren().add(obstacle1.getRectangle());

        // Score Text at the top center of the screen
        scoreText = new Text("Player 1: " + player1Score + " | Player 2: " + player2Score);
        scoreText.setFont(new Font("Arial", 20));
        scoreText.setFill(Color.WHITE);
        scoreText.setLayoutX(250);  // Centered horizontally
        scoreText.setLayoutY(30);   // Positioned near the top
        randBallRoot.getChildren().add(scoreText);

        // Create the randBall scene
        Scene randBallScene = new Scene(randBallRoot, 800, 600);
        randBallRoot.setStyle("-fx-background-color: black;");

        // Handle key presses for player movement
        randBallScene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.W) {
                player1.setDirection(0, -1);  // Move player 1 up
            } else if (e.getCode() == KeyCode.S) {
                player1.setDirection(0, 1);  // Move player 1 down
            } else if (e.getCode() == KeyCode.UP) {
                player2.setDirection(0, -1);  // Move player 2 up
            } else if (e.getCode() == KeyCode.DOWN) {
                player2.setDirection(0, 1);  // Move player 2 down
            }
        });

        randBallScene.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.W || e.getCode() == KeyCode.S) {
                player1.setDirection(0, 0);  // Stop player 1 movement
            }
            if (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.DOWN) {
                player2.setDirection(0, 0);  // Stop player 2 movement
            }
        });

        // Start the game loop
         gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                player1.update();
                player2.update();
                obstacle1.update();

                // Check for collision with top and bottom of the screen
                if (obstacle1.getRectangle().getY() <= 0 ||
                    obstacle1.getRectangle().getY() >= 600 - obstacle1.getRectangle().getHeight()) {
                    obstacle1.setDy(-obstacle1.getDy());  // Reverse direction
                }

                // Check for collision with left and right edges
                if (obstacle1.getRectangle().getX() <= 0) {
                    // Player 2 scores a point if the obstacle hits the left wall
                    player2Score++;
          
               	 if (player1Score == 5||player2Score == 5) { 
               		 
                   	 winner(primaryStage);
                   		 
                   	 }
               	 else {
                    stopGame(primaryStage);  // Return to main menu
               	 }
                    this.stop();  // Stop the game loop to prevent further updates
                }

                if (obstacle1.getRectangle().getX() >= 800 - obstacle1.getRectangle().getWidth()) {
                    // Player 1 scores a point if the obstacle hits the right wall
                    player1Score++;
                    if (player1Score == 5) {
                        gameLoop.stop(); // Stop the game loop
                        winner(primaryStage); // Show winner screen
                    } else if (player2Score == 5) {
                        gameLoop.stop(); // Stop the game loop
                        winner(primaryStage); // Show winner screen
                    } else {
        
                        stopGame(primaryStage); // Go to main menu if no winner
                    }
                    stopGame(primaryStage);  // Return to main menu
                    this.stop();  // Stop the game loop to prevent further updates
                }

                // Check for collision with paddles
                if (player1.Colliding(obstacle1) || player2.Colliding(obstacle1)) {
                    obstacle1.setDx(-obstacle1.getDx());  // Reverse horizontal direction
                }
            }
        };

        gameLoop.start();
        // Switch to randBall scene
        primaryStage.setTitle("RANDOM BALL SIZE");
        primaryStage.setScene(randBallScene);
        primaryStage.show();
    }

 public void multiBall (Stage primaryStage) {
        // Create the randPaddle scene (new game mode)
        Pane multiBallRoot = new Pane();

        // Player 1 (left side)
        player1 = new Player(50, 250, 20, 100);
        player1.getRectangle().setFill(Color.WHITE);
        multiBallRoot.getChildren().add(player1.getRectangle());

        // Player 2 (right side
        player2 = new Player(730, 250, 20, 100);
        player2.getRectangle().setFill(Color.WHITE);
        multiBallRoot.getChildren().add(player2.getRectangle());

        // Obstacle in the middle
        obstacle1 = new obstacles(380, 280); // Center of the screen

        multiBallRoot.getChildren().add(obstacle1.getRectangle());
       
        obstacle2 = new obstacles(100, 380);
        multiBallRoot.getChildren().add(obstacle2.getRectangle());
       
        obstacle3 = new obstacles(200, 480);
        multiBallRoot.getChildren().add(obstacle3.getRectangle());

        // Score Text at the top center of the screen
        scoreText = new Text("Player 1: " + player1Score + " | Player 2: " + player2Score);
        scoreText.setFont(new Font("Arial", 20));
        scoreText.setFill(Color.WHITE);
        scoreText.setLayoutX(250); // Centered horizontally
        scoreText.setLayoutY(30); // Positioned near the top
        multiBallRoot.getChildren().add(scoreText);

        // Create the randPaddle scene

        Scene multiBallScene = new Scene(multiBallRoot, 800, 600);
        multiBallRoot.setStyle("-fx-background-color: black;");

        // Handle key presses for player movement
        multiBallScene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.W) {
                player1.setDirection(0, -1); // Move player 1 up
            } else if (e.getCode() == KeyCode.S) {
                player1.setDirection(0, 1); // Move player 1 down
            } else if (e.getCode() == KeyCode.UP) {
                player2.setDirection(0, -1); // Move player 2 up
            } else if (e.getCode() == KeyCode.DOWN) {
                player2.setDirection(0, 1); // Move player 2 down
            }
        });

        multiBallScene.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.W || e.getCode() == KeyCode.S) {
                player1.setDirection(0, 0);  // Stop player 1 movement

            }

            if (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.DOWN) {
                player2.setDirection(0, 0);  // Stop player 2 movement
            }
        });

        // Start the game loop

         gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                player1.update();
                player2.update();
                
                obstacle1.update();
                obstacle2.update();
                obstacle3.update();

                // Check for collision with top and bottom of the screen
                if (obstacle1.getRectangle().getY() <= 0 ||
                    obstacle1.getRectangle().getY() >= 600 - obstacle1.getRectangle().getHeight()) {
                    obstacle1.setDy(-obstacle1.getDy()); // Reverse direction

                }
               
                if (obstacle2.getRectangle().getY() <= 0 ||
                    obstacle2.getRectangle().getY() >= 600 - obstacle2.getRectangle().getHeight()) {
                    obstacle2.setDy(-obstacle2.getDy()); // Reverse direction

                }
               
                if (obstacle3.getRectangle().getY() <= 0 ||
                    obstacle3.getRectangle().getY() >= 600 - obstacle3.getRectangle().getHeight()) {
                    obstacle3.setDy(-obstacle3.getDy()); // Reverse direction
                }              
                boolean play1Hit = false;
                boolean play2Hit = false;
                // Check for collision with left and right edges (obstacle 1)
                if (obstacle1.getRectangle().getX() == 0) {
                    // Player 2 scores a point if the obstacle hits the left wall
                    play1Hit = true;
                }
                if (obstacle1.getRectangle().getX() == 800 - obstacle1.getRectangle().getWidth()) {
                    // Player 1 scores a point if the obstacle hits the right wall
                    play2Hit = true;
                }
                // Check for collision with left and right edges (obstacle 2)
                if (obstacle2.getRectangle().getX() == 0) {
                    // Player 2 scores a point if the obstacle hits the left wall
                    play1Hit = true;
                }

                if (obstacle2.getRectangle().getX() == 800 - obstacle2.getRectangle().getWidth()) {
                    // Player 1 scores a point if the obstacle hits the right wall
                    play2Hit = true;
                }
              
                // Check for collision with left and right edges (obstacle 3)
                if (obstacle3.getRectangle().getX() == 0) {
                    // Player 2 scores a point if the obstacle hits the left wall
                    play1Hit = true;
                }
                if (obstacle3.getRectangle().getX() == 800 - obstacle3.getRectangle().getWidth()) {
                    // Player 1 scores a point if the obstacle hits the right wall
                    play2Hit = true;
                }    
                if (play2Hit == true) {
                player1Score++;
                if (player1Score == 5) {
                    gameLoop.stop(); // Stop the game loop
                    winner(primaryStage); // Show winner screen
                } else if (player2Score == 5) {
                    gameLoop.stop(); // Stop the game loop
                    winner(primaryStage); // Show winner screen
                } else {
                
                    stopGame(primaryStage); // Go to main menu if no winner
                }
                }
                
                if(play1Hit ==true) {
                	player2Score++;
                    stopGame(primaryStage); // Return to main menu
                }

                // Check for collision with paddles

                if (player1.Colliding(obstacle1) || player2.Colliding(obstacle1)) {

                    obstacle1.setDx(-obstacle1.getDx()); // Reverse horizontal direction

                }
               
                if (player1.Colliding(obstacle2) || player2.Colliding(obstacle2)) {

                    obstacle2.setDx(-obstacle2.getDx()); // Reverse horizontal direction

                }
               
                if (player1.Colliding(obstacle3) || player2.Colliding(obstacle3)) {

                    obstacle3.setDx(-obstacle3.getDx()); // Reverse horizontal direction

                }

            }

        };



        gameLoop.start();



        // Switch to randPaddle scene

        primaryStage.setTitle("MULTIBALL");

        primaryStage.setScene(multiBallScene);

        primaryStage.show();

    }
   
   
 public void dodgeBall(Stage primaryStage) {

	    // Create the randPaddle scene (new game mode)
	    Pane dodgeBallRoot = new Pane();

	    // Player 1 (left side)
	    player1 = new Player(50, 250, 20, 100);
	    player1.getRectangle().setFill(Color.WHITE);
	    dodgeBallRoot.getChildren().add(player1.getRectangle());

	    // Player 2 (right side)
	    player2 = new Player(730, 250, 20, 100);
	    player2.getRectangle().setFill(Color.WHITE);
	    dodgeBallRoot.getChildren().add(player2.getRectangle());

	    // Obstacles in the middle
	    obstacle1 = new obstacles(380, 280); // Center of the screen
	    dodgeBallRoot.getChildren().add(obstacle1.getRectangle());
	   
	    obstacle2 = new obstacles(100, 380);
	    dodgeBallRoot.getChildren().add(obstacle2.getRectangle());
	   
	    obstacle3 = new obstacles(200, 480);
	    dodgeBallRoot.getChildren().add(obstacle3.getRectangle());

	    // Score Text at the top center of the screen
	    scoreText = new Text("Player 1: " + player1Score + " | Player 2: " + player2Score);
	    scoreText.setFont(new Font("Arial", 20));
	    scoreText.setFill(Color.WHITE);
	    scoreText.setLayoutX(250); // Centered horizontally
	    scoreText.setLayoutY(30); // Positioned near the top
	    dodgeBallRoot.getChildren().add(scoreText);

	    // Create the randPaddle scene
	    Scene dodgeBallScene = new Scene(dodgeBallRoot, 800, 600);
	    dodgeBallRoot.setStyle("-fx-background-color: black;");

	    // Handle key presses for player movement
	    dodgeBallScene.setOnKeyPressed(e -> {
	        if (e.getCode() == KeyCode.W) {
	            player1.setDirection(0, -1); // Move player 1 up
	        } else if (e.getCode() == KeyCode.S) {
	            player1.setDirection(0, 1); // Move player 1 down
	        } else if (e.getCode() == KeyCode.UP) {
	            player2.setDirection(0, -1); // Move player 2 up
	        } else if (e.getCode() == KeyCode.DOWN) {
	            player2.setDirection(0, 1); // Move player 2 down
	        }
	    });

	    dodgeBallScene.setOnKeyReleased(e -> {
	        if (e.getCode() == KeyCode.W || e.getCode() == KeyCode.S) {
	            player1.setDirection(0, 0);  // Stop player 1 movement
	        }
	        if (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.DOWN) {
	            player2.setDirection(0, 0);  // Stop player 2 movement
	        }
	    });

	    // Boolean flag to track whether game should be stopped
	    final boolean[] gameStopped = {false};
	    // Start the game loop
	     gameLoop = new AnimationTimer() {
	        @Override
	        public void handle(long now) {
	            if (!gameStopped[0]) {
	                player1.update();
	                player2.update();
	                obstacle1.update();
	                obstacle2.update();
	                obstacle3.update();

	                // Check for collision with sides of the screen
	                if (obstacle1.getRectangle().getY() <= 0 || obstacle1.getRectangle().getY() >= 600 - obstacle1.getRectangle().getHeight()) {
	                    obstacle1.setDy(-obstacle1.getDy()); // Reverse direction vertically
	                }
	                if (obstacle1.getRectangle().getX() <= 0 || obstacle1.getRectangle().getX() >= 800 - obstacle1.getRectangle().getWidth()) {
	                    obstacle1.setDx(-obstacle1.getDx()); // Reverse direction horizontally
	                }

	                if (obstacle2.getRectangle().getY() <= 0 || obstacle2.getRectangle().getY() >= 600 - obstacle2.getRectangle().getHeight()) {
	                    obstacle2.setDy(-obstacle2.getDy()); // Reverse direction vertically
	                }
	                if (obstacle2.getRectangle().getX() <= 0 || obstacle2.getRectangle().getX() >= 800 - obstacle2.getRectangle().getWidth()) {
	                    obstacle2.setDx(-obstacle2.getDx()); // Reverse direction horizontally
	                }

	                if (obstacle3.getRectangle().getY() <= 0 || obstacle3.getRectangle().getY() >= 600 - obstacle3.getRectangle().getHeight()) {
	                    obstacle3.setDy(-obstacle3.getDy()); // Reverse direction vertically
	                }
	                if (obstacle3.getRectangle().getX() <= 0 || obstacle3.getRectangle().getX() >= 800 - obstacle3.getRectangle().getWidth()) {
	                    obstacle3.setDx(-obstacle3.getDx()); // Reverse direction horizontally
	                }

	                // Check for collision with paddles
	                if (player1.Colliding(obstacle1) || player1.Colliding(obstacle2) ||  player1.Colliding(obstacle3)) {
	                    player2Score++;
	               	 if (player1Score == 5||player2Score == 5) { 
	               		 
	                   	 winner(primaryStage);
	                   		 
	                   	 }
	               	 else {
	                    gameStopped[0] = true;
	                    stopGame(primaryStage);
	               	 }
	                }

	                if (player2.Colliding(obstacle1) || player2.Colliding(obstacle2) ||  player2.Colliding(obstacle3)) {
	                    player1Score++;
	                    if (player1Score == 5) {
	                        gameLoop.stop(); // Stop the game loop
	                        winner(primaryStage); // Show winner screen
	                    } else if (player2Score == 5) {
	                        gameLoop.stop(); // Stop the game loop
	                        winner(primaryStage); // Show winner screen
	                    } else {
	                        gameStopped[0] = true; 
	                        stopGame(primaryStage); // Go to main menu if no winner
	                    }
	                }
	            }
	        }
	    };

	    gameLoop.start();
	    // Switch to dodgeBall scene
	    primaryStage.setTitle("DodgeBall");
	    primaryStage.setScene(dodgeBallScene);
	    primaryStage.show();
	}

     public void instruction (Stage primaryStage) {
        // Create the randPaddle scene (new game mode)
        Pane instructionRoot = new Pane();

        instructionText1 = new Text("B.R.A.T. Game is an interactive 2-player game!");
        instructionText2 = new Text("Player 1 will use W & S keys to move the paddle Up and Down \n" +
"Player 2 will use Up & Down arrow keys to move the paddle Up and down");
        instructionText3 = new Text("MODES:");
instructionText4 = new Text("Normal Game: Each player will try to use the paddle on each side of the screen and prevent the white square to \nget the your side of the screen \n\n" +
"Random Size Paddle: The players will be given a random size paddle which they have to prevent the white square \nto reach their side of the screen \n\n" +
"Random Size Ball: The players will be given a random size ball which they have to prevent to reach their side of the \nscreen \n\n" +
"MultiBall: Three balls will spawn in the center of the screen which the players must prevent to reach their side of the \nscreen \n\n" +
"DodgeBall: The players must prevent their paddle from touching the three white squares");

        instructionText1.setFont(new Font("Arial", 24));
        instructionText1.setFill(Color.RED);
        instructionText1.setLayoutX(100); 
        instructionText1.setLayoutY(50); 
        
        instructionText2.setFont(new Font("Arial", 20));
        instructionText2.setFill(Color.WHITE);
        instructionText2.setLayoutX(25);
        instructionText2.setLayoutY(100);
        
        instructionText3.setFont(new Font("Arial", 24));
        instructionText3.setFill(Color.RED);
        instructionText3.setLayoutX(100);
        instructionText3.setLayoutY(200);
        
        instructionText4.setFont(new Font ("Arial", 15));
        instructionText4.setFill(Color.WHITE);
        instructionText4.setLayoutX(25);
        instructionText4.setLayoutY(250);

        instructionRoot.getChildren().add(instructionText1);
        instructionRoot.getChildren().add(instructionText2);
        instructionRoot.getChildren().add(instructionText3);
        instructionRoot.getChildren().add(instructionText4);
        
        // Create the randPaddle scene
        Scene instructionScene = new Scene(instructionRoot, 800, 600);
        instructionRoot.setStyle("-fx-background-color: black;");

Button button7 = new Button ("GO BACK");
        button7.setLayoutX(330);
        button7.setLayoutY(520);
        instructionRoot.getChildren().addAll(button7);

button7.setOnAction(e -> {
    //Switch to normalGame when button is clicked
    stopGame(primaryStage);
});

        // Switch to randPaddle scene
        primaryStage.setTitle("INSTRUCTIONS");
        primaryStage.setScene(instructionScene);
        primaryStage.show();
    }
     public void winner(Stage primaryStage) {
    	    Pane winnerRoot = new Pane();

    	    Text winnerText;
    	    if (player1Score == 5) { 
    	        winnerText = new Text("PLAYER 1 WINS!!!");
    	    } else { 
    	        winnerText = new Text("PLAYER 2 WINS!!!");
    	    }

    	    winnerText.setFont(new Font("Arial", 30));
    	    winnerText.setFill(Color.RED);
    	    winnerText.setLayoutX(250); 
    	    winnerText.setLayoutY(200); 
    	    winnerRoot.getChildren().add(winnerText);

    	    // Play Again button
    	    Button playAgainButton = new Button("Play Again");
    	    playAgainButton.setLayoutX(330);
    	    playAgainButton.setLayoutY(300);
    	    winnerRoot.getChildren().add(playAgainButton);

    	    // Button Action
    	    playAgainButton.setOnAction(e -> {
    	        resetGame(primaryStage);
    	    });

    	    Scene winnerScene = new Scene(winnerRoot, 800, 600);
    	    winnerRoot.setStyle("-fx-background-color: black;");
    	    primaryStage.setScene(winnerScene);
    	    primaryStage.show();
    	}
     
     private void resetGame(Stage primaryStage) {
    	    player1Score = 0;
    	    player2Score = 0;
    	    stopGame(primaryStage);
    	}

     
    private void stopGame(Stage primaryStage) {
        // Return to main menu
    	 if (gameLoop != null) {
    	        gameLoop.stop();
    	    }
        primaryStage.setScene(introScene);
        primaryStage.show();  
        obstacle1.setSpeed(2,2);
        obstacle3.setSpeed(2,2);
        obstacle2.setSpeed(2,2);

    }

    public static void main(String[] args) {

        launch(args);
    }
}