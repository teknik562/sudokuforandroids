package de.fhd.medien.mait.sfa;

import java.util.Random;

/**
 * This class contains all methods to generate a clearly solveable sudokufield
 * @author dac-xp
 *
 */
public class Algo
  {
    // This Array contains the fully generated sudoku-field
    private int field_filled[][] = new int[9][9];
    // mask for numbers which the player can see
    private int field_partial[][] = new int[9][9];
    // field which contains the numbers of the player
    private int field_player[][] = new int[9][9];
    // maximum tries to generate a unique number before going one step back
    private int max_tries = 40;
    
    static final int LEVEL_BASE = 50;
    static final int LEVEL_MULTIPLICATOR = 10;
    
    /**
     * this method fills a sudoku-field with new (random) numbers
     */
    private void fill_field(int _x, int _y)
      {
        // new random-object
        Random rand = new Random();
        
        // try to generate a unique number "max_tries" time
        for(int i = 0; i < this.max_tries; i++)
          {
            // finish procedure if field is filled
            if(this.field_filled[8][8] > 0)
              {
                break;
              }
            
            // generate a random number
            int rand_value = Math.abs(rand.nextInt()) % 9 + 1;
            
            // check if number is unique in same line, column and block
            if(this.check_block(this.field_filled, _x, _y, rand_value) 
                && this.check_line(this.field_filled, _x, rand_value) 
                && this.check_col(this.field_filled, _y, rand_value))
              {
                this.field_filled[_x][_y] = rand_value;
                                
                // if x >= 8 then switch to new line
                if(_x >= 8)
                  {
                    this.fill_field(0, _y+1);
                  }
                else
                  {
                    this.fill_field(_x+1, _y);
                  }
              }
          }
      }
    
    /**
     * this method checks a line for duplicated entries
     * @param _line is the line that will be checked
     * @param _value check if this value is in the line
     * @return returns "true" if there is no such value, else "false"
     */
    private boolean check_line(int[][] field, int _line, int _value)
      {
        // go through all elements
        for(int i = 0; i < field.length; i++)
          {
            if(field[_line][i] == _value)
              return(false);
          }
        return(true);
      }

    /**
     * this method checks a column for duplicated entries
     * @param _col is the column that will be checked
     * @param _value check if this value is in the line
     * @return returns "true" if there is no such value, else "false"
     */
    private boolean check_col(int[][] field, int _col, int _value)
      {
        // go through all elements
        for(int i = 0; i < field.length; i++)
          {
            if(field[i][_col] == _value)
              return(false);
          }
        return(true);
      }
    
    /**
     * this method check a block for duplicated entries
     * @param _line is the line of the block tat will be checked
     * @param _line is the column of the block tat will be checked
     * @param _value check if this value is in the block
     */
    private boolean check_block(int[][] field, int _line, int _col, int _value)
      {
        int block_line = 0;
        int block_col = 0;
        double line = _line;
        double col = _col;

        // get block-line-index (0, 1, 2)
        block_line = (int)Math.ceil(((line+1)/3))-1;
        // get block-column-index (0, 1, 2)
        block_col = (int)Math.ceil(((col+1)/3))-1;

        // go through lines in the block
        for(int l = 0+(block_line)*3; l <= (0+(block_line)*3)+2; l++)
          {
            // go through columns in the block
            for(int c = 0+(block_col)*3; c <= (0+(block_col)*3)+2; c++)
              {
                if(field[l][c] == _value)
                  {
                    return(false);
                  }
              }
          }
        return(true);
      }
    
    /**
     * this method generates the mask for the field
     * which specifies the uncovered numbers
     * 
     * @param _level is the difficulty of the game
     */
    private void generate_mask(int _level)
      {
        // calculate number of given fields
        int num = LEVEL_BASE-LEVEL_MULTIPLICATOR*(_level-1);
        int x, y;
        // create random-object
        Random rand = new Random();
        
        for(int i = 0; i <= num; i++)
          {
            do
              {
                // generate random coordinates
                x = Math.abs(rand.nextInt()) % 9;
                y = Math.abs(rand.nextInt()) % 9;
              }while(this.field_partial[x][y] > 0);
          
            // insert number in mask-field
            this.field_partial[x][y] = this.field_filled[x][y];
            // inset numer in player-field
            this.field_player[x][y] = this.field_filled[x][y];
          }
      }

    /**
     * This method checks if a field is filled complete
     * 
     * @param field is the filed that will be checked
     * @return "true" if it is filled complete else "false"
     */
    private boolean isFilled(int[][] field)
      {
        for(int i = 0; i < field.length; i++)
          for(int j = 0; j < field[0].length; j++)
            if(field[i][j] == 0)
              return(false);
        
        return(true);
      }
    
    /**
     * This method checks if the sudokufield is solveable clearly
     * 
     * @param field sudoku-field that will be checked if it is solveable clearly
     * @return "true" if it is clearly solveable else "false"
     */    
    private boolean isSolveable(int[][] _field)
      {
        // create clone of the field with uncovered numbers
        int solve_field[][] = new int[9][9];
        
        // Copy values of the sudoku-field into the local field
        for(int x = 0; x < solve_field.length; x++)
          for(int y = 0; y < solve_field.length; y++)
            solve_field[x][y] = _field[x][y];
                                         
        // counter for numbers of possibilities in a round (0 = not solveable / >1 = ambivalent solveable)
        int alt_counter = 0;
        // contains number that will be written into a field
        int solution_of_field = 0;
        
        // play this sudoku-field as long as there is at minimum
        // one clear resolution
        do
          {
            alt_counter = 0;
            
            // go through all fields an try to get a clear resolution
            for(int i = 0; i < solve_field.length; i++)
              {
                for(int j = 0; j < solve_field[0].length; j++)
                  {
                    alt_counter = 0;
                    
                    if(solve_field[i][j] == 0)
                      {
                        // try if exactly one Number fits the field
                        //  => go through all alternatives (1-9) and increment counter for 
                        // each possible resolution
                        for(int number = 1; number <= 9; number++)
                          {
                            // check if number is possible
                            if(this.check_block(solve_field, i, j, number)
                                && this.check_line(solve_field, i, number)
                                && this.check_col(solve_field, j, number))
                              {
                                // increment counter
                                alt_counter++;
                                solution_of_field = number;
                              }
                          }
                      }
                    
                    // if counter = 1 then write number into field and break
                    if(alt_counter == 1)
                      {
                        solve_field[i][j] = solution_of_field;
                        break;
                      }
                  }
                // if counter = 1 then break
                if(alt_counter == 1)
                  {
                    break;
                  }
              }
            
          }while(alt_counter == 1 && !this.isFilled(solve_field));
        
        // if field is solveable (loop above prooves it) return "true"
        if(this.isFilled(solve_field))
          {
            return(true);
          }
        
        return(false);
      }
    
    // <<<<<<<<<<<<<< INTERFACES >>>>>>>>>>>>>>>>>>>
    
    /**
     * This method create a clearly solveable sudokufield and
     * save it when an object is created
     * 
     * @param _level is the degree of difficulty (1 = easy, 2 = normal, 3 = hard)
     */
    public Algo(int _level)
      {
        // try to generate a field as often as it needs to
        // get a complete filled field
        while(!this.isFilled(this.field_filled))
          {
            this.field_filled = new int[9][9];
            this.fill_field(0, 0);
          }
        
        // try to generate a sudoku-mask until it is
        // clearly solveable
        while(!this.isSolveable(this.field_partial))
          {
            this.field_partial = new int[9][9];
            this.generate_mask(_level);
          }
      }
    
    /**
     * @return this method returns the created field
     */
    public int[][] getFilledField()
      {
        return this.field_filled;
      }
    
    /**
     * @return this method returns the masked sudoku-field which is clearly solveable
     */
    public int[][] getMaskedField()
      {
        return this.field_partial;
      }
    
    /**
     * checks if a number is legal in a given field
     * 
     * @param _x line of the number (0 - 8)
     * @param _y column of the number (0 - 8)
     * @param _number number that has to be checked (1 - 9)
     * @return "true" if it is legal; "false" if it is not legal
     */
    public boolean isLegal(int _x, int _y, int _number)
      {
        if(this.field_filled[_x][_y] == _number)
          return(true);
        
        return(false);
      }
    
    /**
     * check if resolution of player and generated sudoku field are equal
     * 
     * @return "true" if resolution is correct; else "false"
     */
    public boolean isCorrect()
      {
        // go through all fields
        for(int i = 0; i < this.field_filled.length; i++)
          for(int j = 0; j < this.field_filled[0].length; j++)
            // check if number in player-field and numer in generated field are equal
            if(this.field_filled[i][j] != this.field_player[i][j])
              return(false);
        
        return(true);
      }
    
    /**
     * this method sets the value of an item in the player-field
     * 
     * @param _x line of the item
     * @param _y column of the item
     * @param _value new value of the item
     */
    public void setFieldValue(int _x, int _y, int _value)
      {
        // only set new value if item is not given
        if(this.field_partial[_x][_y] <= 0)
          this.field_player[_x][_y] = _value;
      }
    
    /**
     * gets the value of an item of the generated field.
     * this method can be used as cheat
     * 
     * @param _x line of the item
     * @param _y column of the item
     * @return returns the value of an item
     */
    public int getFieldValue(int _x, int _y)
      {
        return(this.field_filled[_x][_y]);
      }
  }