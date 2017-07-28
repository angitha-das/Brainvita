package com.example.angitha.mygame.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;


/**
 * PegView is a subclassed ImageView for storing the Pegs
 * Created by angitha on 1/7/17.
 *
 */
public class PegView extends android.support.v7.widget.AppCompatImageView {


	private int row;
	private int col;

	/**
	 * Takes row and column as well as superclass constructor
	 *
	 * @param context
	 * @param r
	 * @param c
	 */
	public PegView(Context context, int r, int c) {
		super(context);
		row = r;
		col = c;
	}

	/**
	 * Takes row and column as well as superclass constructor
	 *
	 * @param context
	 * @param attrs
	 * @param r
	 * @param c
	 */
	public PegView(Context context, AttributeSet attrs, int r, int c) {
		super(context, attrs);
		row = r;
		col = c;
	}

	/**
	 * Takes row and column as well as superclass constructor
	 *
	 * @param context
	 * @param attrs
	 * @param defStyle
	 * @param r
	 * @param c
	 */
	public PegView(Context context, AttributeSet attrs, int defStyle, int r, int c) {
		super(context, attrs, defStyle);
		row = r;
		col = c;
	}


	/**
	 * Setter for row of Peg in table
	 *
	 * @param r
	 */
	public void setRow(int r) {
		row = r;
	}

	/**
	 * Getter for row of Peg in table
	 *
	 * @return row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Setter for column of Peg in table
	 *
	 * @param c
	 */
	public void setColumn(int c) {
		col = c;
	}

	/**
	 * Getter for column of Peg in table
	 *
	 * @return col
	 */
	public int getColumn() {
		return col;
	}

	/**
	 * Move method, takes original square and new square to go to as well as array of PegLayouts in board
	 * Checks whether square to move to is empty and in right place as well as whether there is a Peg to jump over
	 * Sets new row and col values for PegView
	 *
	 * @param oldSquare
	 * @param newSquare
	 * @param squares
	 * @return bool
	 */
	public boolean move(PegLayout oldSquare, PegLayout newSquare, PegLayout[][] squares, int grid[][]) {

		int newRow = newSquare.getRow();
		int newCol = newSquare.getColumn();

		int oldRow = oldSquare.getRow();
		int oldCol = oldSquare.getColumn();


		if (newSquare.isEmpty()) {
			if (((Math.abs(newRow - oldRow) == 2) && (newCol == oldCol)) ||
					(Math.abs(newCol - oldCol) == 2) && (newRow == oldRow)) {
				if ((oldCol - newCol == -2) && (!squares[newRow][newCol - 1].isEmpty())) {
					squares[newRow][newCol - 1].removeAllViews();
					grid[newRow][newCol - 1] = 0;
				} else if ((oldCol - newCol == 2) && (!squares[newRow][newCol + 1].isEmpty())) {
					squares[newRow][newCol + 1].removeAllViews();
					grid[newRow][newCol + 1] = 0;
				} else if ((oldRow - newRow == -2) && (!squares[newRow - 1][newCol].isEmpty())) {
					squares[newRow - 1][newCol].removeAllViews();
					grid[newRow - 1][newCol] = 0;
				} else if ((oldRow - newRow == 2) && (!squares[newRow + 1][newCol].isEmpty())) {
					squares[newRow + 1][newCol].removeAllViews();
					grid[newRow + 1][newCol] = 0;
				} else {
					return false;
				}
				oldSquare.removeView(this);
				grid[oldRow][oldCol] = 0;
				newSquare.addView(this);
				grid[newCol][oldCol] = 1;
				this.row = newRow;
				this.col = newCol;
				this.setVisibility(View.VISIBLE);
				return true;
			}

		}
		return false;
	}

	public boolean haveILost(int[][] inputArr) {
		for (int i = 0; i < inputArr.length; i++) {
			for (int j = 0; j < inputArr[i].length; j++) {
				if (inputArr[i][j] == 1) {
					return (inputArr[i][j - 1] == 1 && inputArr[i][j - 2] == 2) ||
							(inputArr[i][j + 1] == 1 && inputArr[i][j + 2] == 2) ||
							(inputArr[i + 1][j] == 1 && inputArr[i + 2][j] == 2) ||
							(inputArr[i - 1][j] == 1 && inputArr[i - 2][j] == 2);
				}
			}
		}
		return false;
	}
}

