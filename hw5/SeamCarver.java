import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

public class SeamCarver {
    private int width;
    private int height;
    private Picture picture;
    private double[][] energy;
    private double[][] M;
    private double aboveLeft = 0.0;
    private double aboveRight = 0.0;
    private double aboveCenter;
    private int indexOfMinEnergy = 0;
    private double largerValue = Double.MAX_VALUE;
    private double minEnergy;
    private int lastColumn = 0;
    public SeamCarver(Picture picture) {
        this.picture = new Picture(picture);
        this.width = picture.width();
        this.height = picture.height();
        energy = new double[height][width];
        M = new double[height][width];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                energy[j][i] = 0.0;
                M[j][i] = 0.0;
            }
        }
    }
    public Picture picture() {
        return new Picture(this.picture);
    }
    public int width() {
        return width;
    }
    public int height() {
        return height;
    }
    public double energy(int x, int y) {
        if (x < 0 || x > width() - 1 || y < 0 || y > height() - 1) {
            throw new java.lang.IndexOutOfBoundsException();
        } else {
            Color topPixel;
            Color bottomPixel;
            Color leftPixel;
            Color rightPixel;

            //for border values
            if (picture.width() == 1) {
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
                int rY = bottomPixel.getRed() - topPixel.getRed();
                int gY = bottomPixel.getGreen() - topPixel.getGreen();
                int bY = bottomPixel.getBlue() - topPixel.getBlue();
                double energyY = (rY * rY) + (gY * gY) + (bY * bY);
                energy[y][x] = energyY;
                return energy[y][x];
            } else if (picture.height() == 1) {
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
                int rX = rightPixel.getRed() - leftPixel.getRed();
                int gX = rightPixel.getGreen() - leftPixel.getGreen();
                int bX = rightPixel.getBlue() - leftPixel.getBlue();
                double energyX = (rX * rX) + (gX * gX) + (bX * bX);
                energy[y][x] = energyX;
                return energy[y][x];
            } else {
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

                int rX = rightPixel.getRed() - leftPixel.getRed();
                int gX = rightPixel.getGreen() - leftPixel.getGreen();
                int bX = rightPixel.getBlue() - leftPixel.getBlue();
                int rY = bottomPixel.getRed() - topPixel.getRed();
                int gY = bottomPixel.getGreen() - topPixel.getGreen();
                int bY = bottomPixel.getBlue() - topPixel.getBlue();
                double energyX = (rX * rX) + (gX * gX) + (bX * bX);
                double energyY = (rY * rY) + (gY * gY) + (bY * bY);
                energy[y][x] = (energyX + energyY);
            }
            return energy[y][x];
        }
    }
    public int[] findHorizontalSeam() {
        Picture transposedPicture = new Picture(height(), width());
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Color pixelColors = picture.get(col, row);
                transposedPicture.set(row, col, pixelColors);
            }
        }
        SeamCarver seam = new SeamCarver(transposedPicture);
        return seam.findVerticalSeam();
    }
    public int[] findVerticalSeam() {
        int[] verticalSeam = new int[picture.height()];
        if (picture.width() == 1) {
            for (int row = 0; row < picture.height(); row++) {
                if (row == 0) {
                    M[row][0] = energy(0, row);
                } else {
                    M[row][0] = energy(0, row) + M[row - 1][0];
                }
            }
            return verticalSeam;
        }
        for (int row = 0; row < picture.height(); row++) {
            for (int col = 0; col < picture.width(); col++) {
                if (row == 0) {
                    M[row][col] = energy(col, row);
                } else {
                    if (col == 0) {
                        aboveRight = M[row - 1][col + 1];
                        aboveCenter = M[row - 1][col];
                        minEnergy = Math.min(aboveCenter, aboveRight);
                    } else if (col == width - 1) {
                        aboveLeft = M[row - 1][col - 1];
                        aboveCenter = M[row - 1][col];
                        minEnergy = Math.min(aboveLeft, aboveCenter);
                    } else {
                        aboveLeft = M[row - 1][col - 1];
                        aboveRight = M[row - 1][col + 1];
                        aboveCenter = M[row - 1][col];
                        minEnergy = Math.min(aboveLeft, Math.min(aboveCenter, aboveRight));
                    }
                    M[row][col] = energy(col, row) + minEnergy;
                }
            }
        }
        for (int col = 0; col < picture.width() - 1; col++) {
            if (Math.min(M[height - 1][col], M[height - 1][col + 1]) < largerValue) {
                largerValue = Math.min(M[height - 1][col], M[height - 1][col + 1]);
                if (Math.min(M[height - 1][col], M[height - 1][col + 1]) == M[height - 1][col]) {
                    lastColumn = col;
                } else if (Math.min(M[height - 1][col], M[height - 1][col + 1])
                        == M[height - 1][col + 1]) {
                    lastColumn = col + 1;
                }
            }
        }
        verticalSeam[height - 1] = lastColumn;
        int secondRow = height - 2;
        for (int i = secondRow; i >= 0; i--) {
            double save = M[i][lastColumn];
            for (int col = 0; col < width(); col++) {
                double check = M[i][col];
                if (col == lastColumn - 1 || col == lastColumn + 1) {
                    if (check < save) {
                        save = M[i][col];
                        lastColumn = col;
                    }
                }
            }
            verticalSeam[i] = lastColumn;
        }
        return verticalSeam;
    }
    public void removeHorizontalSeam(int[] seam) {
        SeamRemover.removeHorizontalSeam(this.picture, seam);
    }
    public void removeVerticalSeam(int[] seam) {
        SeamRemover.removeVerticalSeam(this.picture, seam);
    }
}

//        for (int row = picture.height() - 1; row > 0; row--) {
//            for (int col = 0; col < picture.width(); col++) {
//                if (M[row][col] == largerValue) {
//                    aboveCenter = M[row - 1][col];
//                    indexOfMinEnergy = col;
//                    if (col == 0) {
//                        if (M[row - 1][col + 1] < aboveCenter) {
//                            indexOfMinEnergy = col + 1;
//                        }
//                    } else if (col == width - 1) {
//                        if (M[row - 1][col - 1] < aboveCenter) {
//                            indexOfMinEnergy = col - 1;
//                        }
//                    } else {
//                        if (M[row - 1][col - 1] < aboveCenter) {
//                            indexOfMinEnergy = col - 1;
//                            if (M[row - 1][col + 1] < M[row - 1][col - 1]) {
//                                indexOfMinEnergy = col + 1;
//                            }
//                        }
//                        if (M[row - 1][col + 1] < aboveCenter) {
//                            indexOfMinEnergy = col + 1;
//                            if (M[row - 1][col - 1] < M[row - 1][col + 1]) {
//                                indexOfMinEnergy = col - 1;
//                            }
//                        }
//                    }
//                }
//            }
//            verticalSeam[row - 1] = indexOfMinEnergy;
//        }