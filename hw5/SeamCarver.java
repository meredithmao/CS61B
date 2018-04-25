import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

public class SeamCarver {
    private int width;
    private int height;
    private Picture picture;
    private double[][] energy;
    private double[][] M;
    public SeamCarver(Picture picture) {
        this.picture = picture;
        this.width = picture.width();
        this.height = picture.height();
        energy = new double[width][height];
        M = new double[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                energy[i][j] = 0.0;
            }
        }
    }
    public Picture picture() {
        return picture;
    }
    public int width() {
        return width;
    }
    public int height() {
        return height;
    }
    public double energy(int x, int y) {
        if (x < 0 || x > width - 1 || y < 0 || y > height - 1) {
            throw new java.lang.IndexOutOfBoundsException();
        } else {
            Color topPixel;
            Color bottomPixel;
            Color leftPixel;
            Color rightPixel;

            //for border values
            if (x == 0) {
                leftPixel = picture.get(width - 1, y);
                rightPixel = picture.get(x + 1, y);
            } else if (x == picture.width() - 1) {
                leftPixel = picture.get(x - 1, y);
                rightPixel = picture.get(0, y);
            } else {
                leftPixel = picture.get(x - 1, y);
                rightPixel = picture.get(x + 1, y);
            }
            if (y == 0) {
                topPixel = picture.get(x, height - 1);
                bottomPixel = picture.get(x, y + 1);
            } else if (y == picture.height() - 1) {
                topPixel = picture.get(x, y - 1);
                bottomPixel = picture.get(x, 0);
            } else {
                topPixel = picture.get(x, y - 1);
                bottomPixel = picture.get(x, y + 1);
            }

            int Rx = Math.abs(rightPixel.getRed() - leftPixel.getRed());
            int Gx = Math.abs(rightPixel.getGreen() - leftPixel.getGreen());
            int Bx = Math.abs(rightPixel.getBlue() - leftPixel.getBlue());
            int Ry = Math.abs(bottomPixel.getRed() - topPixel.getRed());
            int Gy = Math.abs(bottomPixel.getGreen() - topPixel.getGreen());
            int By = Math.abs(bottomPixel.getBlue() - topPixel.getBlue());
            double energyX = Math.pow(Rx, 2) + Math.pow(Gx, 2) + Math.pow(Bx, 2);
            double energyY = Math.pow(Ry, 2) + Math.pow(Gy, 2) + Math.pow(By, 2);
            energy[x][y] = (energyX + energyY);
        }
        return energy[x][y];
    }
    public int[] findHorizontalSeam() {
        int[] horizontalSeam = new int[picture.width()];
        Picture transposedPicture = new Picture(picture.height(), picture.width());

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Color pixelColors = picture.get(col, row);
                transposedPicture.set(row, col, pixelColors);
            }
        }
        SeamCarver seam = new SeamCarver(transposedPicture);
        horizontalSeam = seam.findVerticalSeam();

        return horizontalSeam;
    }
    public int[] findVerticalSeam() {
        int[] verticalSeam = new int[picture.height()];
        int [][] truthArray = new int[width][height];
        int colBefore = -1;
        int colBelow = 0;
        int colAfter = 1;
        double minEnergy;
        int min = 0;
        for (int row = 0; row < width; row++) {
            for (int col = 0; col < height; col++) {
                if (row == 0) {
                    M[row][col] = energy(row, col);
                } else {
                    minEnergy = Math.min(M[row - 1][col - 1], Math.min(M[row][col - 1], M[row + 1][col - 1]));
                    if (minEnergy == M[row - 1][col - 1]) {
                        truthArray[row][col] = colBefore;
                    } else if (minEnergy == M[row - 1][col]) {
                        truthArray[row][col] = colBelow;
                    } else if (minEnergy == M[row - 1][col + 1]) {
                        truthArray[row][col] = colAfter;
                    }
                }
                M[row][col] = energy(row, col) + minEnergy;
            }
        }

        int arrayValues = height - 1;
        for (int height = 0; height < picture.height(); height++) {
            if (truthArray[])
            return verticalSeam;
        }
    }
    public void removeHorizontalSeam(int[] seam) {
        Picture removedSeam = new Picture(width, height - 1);
        int value = 0;
        for (int col = 0; col < width - 1; col++) {
            for (int row = 0; row < height; row++) {
                if (row == seam[value]) {
                    continue;
                }
                Color pixelColors = picture.get(col, row);
                removedSeam.set(col, row, pixelColors);
            }
            value++;
        }
        picture = removedSeam;
    }
    public void removeVerticalSeam(int[] seam) {
        Picture removedSeam = new Picture(width - 1, height);
        int value = 0;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width - 1; row++) {
                if (col == seam[value]) {
                    continue;
                }
                Color pixelColors = picture.get(col, row);
                removedSeam.set(col, row, pixelColors);
            }
            value++;
        }
        picture = removedSeam;
    }
}
