//L3 - Ball Brick Game

import java.util.*;

class BallBrick{
	static int brickCount = 0;
	static int baseSize = 1;
	static int ballAt = 0;
	static int brickStrength = 0;
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);

		System.out.print("Enter size of the NxN matrix : ");
		int n = sc.nextInt();sc.nextLine();
		String[][] grid = new String[n][n];
		for(int i = 0;i < n;i++){
			for(int j = 0;j < n;j++){
				if(i == 0 || j == n-1 || j == 0)
					grid[i][j] = " W";
				else if(i == n-1 && j != 0 && j != n-1){
					grid[i][j] = " G";
					if(j == n/2) grid[i][j] = " o";
				}else
					grid[i][j] = "  ";
			}
		}
		
		char choice;
		
		do{
			System.out.print("Enter the brick's position and the brick type : ");
			String[] input = sc.nextLine().split(" ");
			int pos_i = Integer.parseInt(input[0]);
			int pos_j = Integer.parseInt(input[1]);
			String type = input[2].length() == 1 ? " " + input[2] : input[2];
			if(type.equals(" R")) brickStrength = 1;
			grid[pos_i][pos_j] = type;
			brickCount++;
			System.out.print("Do you want to continue(Y or N)?");
			choice = sc.nextLine().charAt(0);
		}while(choice == 'Y');
		System.out.print("Enter ball Count : ");
		int ballCount = sc.nextInt();sc.nextLine();
		
		displayBoard(grid);
		ballAt = n/2;
		do{
			if(ballCount < 1){
				System.out.println("Game Over!.");
				break;
			}
			System.out.print("\nEnter the direction in which the ball need to traverse : ");
			String direction = sc.nextLine();
			if(direction.equals("ST")){
				boolean hit = false;
				int wallHit = 0;
				int j = ballAt;
				int dir = 0;
				int dir_i = -1;
				int i = n-1;
				while(i >= 0){
					if(i == 0){
						dir = 0;
						dir_i = 1;
						hit = true;
					}

					if(dir_i == 1 && i == n-1){
						if(!grid[n-1][j].equals(" G")){
							grid[n-1][ballAt] = " _";
							grid[n-1][j] = " o";
							ballAt = j;
							break;
						}else{
							ballCount--;
							break;
						}
					}
					//System.out.println(i + "  " + j);
					if(Character.isDigit(grid[i][j].charAt(1))){
						grid[i][j] = " "+(grid[i][j].charAt(1) == '1' ? " " : grid[i][j].charAt(1) - '1');
						brickCount--;
						hit = true;
						if(j == n/2) break;
					}else if(grid[i][j].equals("DE") || grid[i][j].equals("DS")){
						demolish(grid,i,j);
						hit = true;
						if(!grid[n-1][j].equals(" G")) break;
						else{
							ballCount--;
							break;
						}
					}else if(grid[i][j].equals(" B")){
						grid[i][j] = "  ";
						brickCount--;
						int addAt = baseSize % 2 != 0 ? baseSize/2 + 1 : -(baseSize/2);
						grid[n-1][n/2+addAt] = " _";
						baseSize++;
						dir_i = 1;
						dir = 0;hit = true;
					}else if(grid[i][j].equals(" N")){
						grid[i][j] = "  ";
						dir_i = -1;
						dir = 0;
					}else if(grid[i][j].equals(" E")){
						grid[i][j] = "  ";
						dir_i = 0;
						dir = 1;hit = true;
					}else if(grid[i][j].equals(" W") && i != 0 && i != n-1 && j != 0 && j != n-1){
						grid[i][j] = "  ";
						dir_i = 0;
						dir = -1;hit = true;
					}else if(grid[i][j].equals(" S")){
						grid[i][j] = "  ";
						dir_i = 1;
						dir = 0;hit = true;
					}else if(grid[i][j].equals(" R")){
						if(hit){
							if(brickStrength == 1){
								grid[i][j] = "  ";
								brickCount--;
							}brickStrength--;
						}else brickStrength++;
						dir_i = 1;
						dir = 0;
					}else if(grid[i][j].equals(" D")){
						grid[i][j] = "  ";
						brickCount--;ballCount++;
						dir_i = 1;
						dir = 0;hit = true;
					}
					if(j == 0){
						dir = 1;
						dir_i = 0;hit = true;
					}
					else if(wallHit == 1 && j == 0 || j == n-1){
						ballCount--;
						break;
					}else if(wallHit == 0 && j == 0 || j == n-1){
						wallHit++;hit = true;
					}
					j += dir;
					i += dir_i;		
				}
			}else if(direction.equals("LD")){
				boolean hit = false;
				int j = ballAt;
				int dir = -1;
				int dir_i = -1;
				int i = n-1;
				while(i >= 0){
					if(i == 0){
						dir = 0;
						dir_i = 1;hit = true;
					}
					if(dir_i == 1 && i == n-1){
						if(!grid[n-1][j].equals(" G")){
							grid[n-1][ballAt] = " _";
							grid[n-1][j] = " o";
							ballAt = j;
							break;
						}else{
							ballCount--;
							break;
						}
					}

					if(dir_i == 1 && i == n-1){
						if(!grid[n-1][j].equals(" G")){
							grid[n-1][ballAt] = " _";
							grid[n-1][j] = " o";
							ballAt = j;
							break;
						}else{
							ballCount--;
							break;
						}
					}
					//System.out.println(i + "  " + j);

					if(Character.isDigit(grid[i][j].charAt(1))){
						grid[i][j] = " "+(grid[i][j].charAt(1) == '1' ? " " : grid[i][j].charAt(1) - '1');
						brickCount--;
						dir_i = 1;
						dir = 0;
					}else if(grid[i][j].equals("DE") || grid[i][j].equals("DS")){
						demolish(grid,i,j);
						dir_i = 1;
						dir = 0;
					}else if(grid[i][j].equals(" B")){
						grid[i][j] = "  ";
						brickCount--;
						int addAt = baseSize % 2 != 0 ? baseSize/2 + 1 : -(baseSize/2);
						grid[n-1][n/2+addAt] = " _";
						baseSize++;
						dir_i = 1;
						dir = 0;
					}else if(grid[i][j].equals(" N")){
						grid[i][j] = "  ";
						dir_i = -1;
						dir = 0;
					}else if(grid[i][j].equals(" E")){
						grid[i][j] = "  ";
						dir_i = 0;
						dir = 1;
					}else if(grid[i][j].equals(" W") && i != 0 && i != n-1 && j != 0 && j != n-1){
						grid[i][j] = "  ";
						dir_i = 0;
						dir = -1;
					}else if(grid[i][j].equals(" S")){
						grid[i][j] = "  ";
						dir_i = 1;
						dir = 0;
					}else if(grid[i][j].equals(" R")){
						if(hit){
							if(brickStrength == 1){
								grid[i][j] = "  ";brickCount--;
							}brickStrength--;
						}else brickStrength++;
						dir_i = 1;
						dir = 0;
					}else if(grid[i][j].equals(" D")){
						grid[i][j] = "  ";
						brickCount--;ballCount++;
						dir_i = 1;
						dir = 0;hit = true;
					}
					if(j == 0){
						dir = 1;
						dir_i = 0;hit = true;
					}
					else if(j == n-1){
						ballCount--;
						break;
					}
					j += dir;
					i += dir_i;	
				}
			}else if(direction.equals("RD")){
				boolean hit = false;
				int j = ballAt;
				int dir = 1;
				int dir_i = -1;
				int i = n-1;
				while(i >= 0){
					if(i == 0){
						dir = 0;
						dir_i = 1;
					}

					if(dir_i == 1 && i == n-1){
						if(!grid[n-1][j].equals(" G")){
							grid[n-1][ballAt] = " _";
							grid[n-1][j] = " o";
							ballAt = j;
							break;
						}else{
							ballCount--;
							break;
						}
					}//System.out.println(i + "  " + j);

					if(Character.isDigit(grid[i][j].charAt(1))){
						grid[i][j] = " "+(grid[i][j].charAt(1) == '1' ? " " : grid[i][j].charAt(1) - '1');
						brickCount--;
						dir_i = 1;
						dir = 0;
					}else if(grid[i][j].equals("DE") || grid[i][j].equals("DS")){
						demolish(grid,i,j);
						dir_i = 1;
						dir = 0;
					}else if(grid[i][j].equals(" B")){
						grid[i][j] = "  ";
						brickCount--;
						int addAt = baseSize % 2 != 0 ? baseSize/2 + 1 : -(baseSize/2);
						grid[n-1][n/2+addAt] = " _";
						baseSize++;
						dir_i = 1;
						dir = 0;
					}else if(grid[i][j].equals(" N")){
						grid[i][j] = "  ";
						dir_i = -1;
						dir = 0;
					}else if(grid[i][j].equals(" E")){
						grid[i][j] = "  ";
						dir_i = 0;
						dir = 1;
					}else if(grid[i][j].equals(" W") && i != 0 && i != n-1 && j != 0 && j != n-1){
						grid[i][j] = "  ";
						dir_i = 0;
						dir = -1;
					}else if(grid[i][j].equals(" S")){
						grid[i][j] = "  ";
						dir_i = 1;
						dir = 0;
					}else if(grid[i][j].equals(" R")){
						if(hit){
							if(brickStrength == 1){
								grid[i][j] = "  ";brickCount--;
							}brickStrength--;
						}else brickStrength++;
						dir_i = 1;
						dir = 0;
					}else if(grid[i][j].equals(" D")){
						grid[i][j] = "  ";
						brickCount--;
						ballCount++;
						dir_i = 1;
						dir = 0;hit = true;
					}
					if(j == n-1){
						dir = -1;
						dir_i = 0;hit = true;
					}
					else if(j == 0){
						ballCount--;
						break;
					}
					j += dir;
					i += dir_i;	
				}
			}
			moveBricks(grid);
			displayBoard(grid);
			if(gameOver(grid)){
				System.out.println("GAME OVER ..!!");
				return;
			}
			System.out.println("Ball count is "+ballCount+". Brick count is "+brickCount+".");
			if(brickStrength > 0) System.out.println("R brick strength is "+brickStrength+".");
			if(brickCount < 1){
				System.out.println("You win HURRAY..!!");
				break;
			}
			
			System.out.print("\nDo you want to continue(Y or N)?");
			choice = sc.nextLine().charAt(0);
		}while(choice == 'Y');
		
	}
	static void displayBoard(String[][] grid){
		int n = grid.length;
		System.out.println();
		for(int i = 0;i < n;i++){
			for(int j = 0;j < n;j++)
				System.out.print(grid[i][j]+ " ");
			System.out.println();
		}
	}
	static void demolish(String[][] grid,int x,int y){
		int n = grid.length;
		if(grid[x][y].equals("DE")){
			for(int j = 1;j < n-1;j++){
				if(!grid[x][j].equals("  ")){
					grid[x][j] = "  ";
					brickCount--;
				}
			}
		}else if(grid[x][y].equals("DS")){
			int[][] dir = {{1,0},{0,1},{-1,0},{0,-1},{-1,-1},{1,1},{-1,1},{1,-1}};
			for(int[] dr : dir){
				int i = x+dr[0];
				int j = y+dr[1];
				if(isInBound(n,i,j)){
					if(!grid[i][j].equals("  ")){
						grid[i][j] = "  ";
						brickCount--;
					}
				}
			}
		}
		grid[x][y] = "  ";
		brickCount--;
	}
	static boolean isInBound(int n,int x,int y){
		return x < n-1 && y < n-1 && x > 0 && y > 0;
	}
	static void moveBricks(String[][] grid){
		int n = grid.length;
		for(int i = n-2;i > 0;i--){
			for(int j = 1;j < n-2;j++){
				if(!grid[i][j].equals("  ")){
					grid[i+1][j] = grid[i][j];
					grid[i][j] = "  ";
				}
			}
		}
		
	}
	static boolean gameOver(String[][] grid){
		int n = grid.length;
		int i = n-1;
		for(int j = 1;j < n-2;j++){
			if(!grid[i][j].equals(" G") && !grid[i][j].equals(" _") && !grid[i][j].equals(" o")){
				return true;
			}
		}
		return false;
	}
}

		