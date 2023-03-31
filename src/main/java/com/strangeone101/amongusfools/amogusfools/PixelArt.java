package com.strangeone101.amongusfools.amogusfools;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;

public class PixelArt {

    public static final String[] ART =
    {       "   ######    ",
            "  #XXXXXX#   ",
            " #XXXXXXXU#  ",
            " #####XXXU#  ",
            "#000CC#XXU## ",
            "#CCCCK#XXU#X#",
            "#KKKKK#XXU#X#",
            " #####XXXU#U#",
            " #XXXXXXXU#U#",
            " #XXXXXXXU#U#",
            " #XXXXXXXU#U#",
            " #XUUUUUUU## ",
            " #UUU###UU#  ",
            " #UUU# #UU#  ",
            "  ###   ##   "
    };

    public static Material[][] ART_3D;

    public static final int X_LENGTH = 13;
    public static final int Y_LENGTH = 15;

    static {
        ART_3D = new Material[X_LENGTH][Y_LENGTH];

        for (int i = 0; i < X_LENGTH; i++) { //X
            for (int j = 0; j < Y_LENGTH; j++) {
                char c = ART[j].split("")[i].charAt(0);
                Material mat = Material.AIR;
                switch (c) {
                    case '#':
                        mat = Material.BLACK_CONCRETE; break;
                    case 'X':
                        mat = Material.RED_CONCRETE_POWDER; break;
                    case 'U':
                        mat = Material.RED_CONCRETE; break;
                    case '0':
                        mat = Material.WHITE_CONCRETE_POWDER; break;
                    case 'C':
                        mat = Material.CYAN_CONCRETE_POWDER; break;
                    case 'K':
                        mat = Material.CYAN_CONCRETE; break;
                    default:
                        mat = Material.AIR; break;
                }

                ART_3D[i][j] = mat;
            }
        }
    }

    public static void displayAmogus(Location baseLocation, float angle, int color) {
        final float HALF_X = (float)X_LENGTH / 2;


        for (int i = 0; i < X_LENGTH; i++) {
            for (int j = 0; j < Y_LENGTH; j++) {
                double x = Math.cos(Math.toRadians(angle)) * (i - HALF_X);
                double y = Y_LENGTH - j;
                double z = Math.sin(Math.toRadians(angle)) * (i - HALF_X);

                Material mat = ART_3D[i][j];

                if (mat != Material.AIR && mat != null) {
                    Location displayLoc = baseLocation.clone().add(x, y - 0.5, z);

                    if (mat == Material.RED_CONCRETE_POWDER) mat = getColor(color, true);
                    else if (mat == Material.RED_CONCRETE) mat = getColor(color, false);

                    baseLocation.getWorld().spawnParticle(Particle.BLOCK_MARKER, displayLoc, 1, mat.createBlockData());
                }

            }
        }
    }

    protected static Material getColor(int color, boolean powder) {
        if (powder) {
            switch (color % 10) {
                default:
                case 0: return Material.RED_CONCRETE_POWDER;
                case 1: return Material.ORANGE_CONCRETE_POWDER;
                case 2: return Material.YELLOW_CONCRETE_POWDER;
                case 3: return Material.LIME_CONCRETE_POWDER;
                case 4: return Material.BLUE_CONCRETE_POWDER;
                case 5: return Material.LIGHT_BLUE_CONCRETE_POWDER;
                case 6: return Material.PURPLE_CONCRETE_POWDER;
                case 7: return Material.PINK_CONCRETE_POWDER;
                case 8: return Material.MAGENTA_CONCRETE_POWDER;
                case 9: return Material.GRAY_CONCRETE_POWDER;
            }
        }

        switch (color % 10) {
            default:
            case 0: return Material.RED_CONCRETE;
            case 1: return Material.ORANGE_CONCRETE;
            case 2: return Material.YELLOW_CONCRETE;
            case 3: return Material.LIME_CONCRETE;
            case 4: return Material.BLUE_CONCRETE;
            case 5: return Material.LIGHT_BLUE_CONCRETE;
            case 6: return Material.PURPLE_CONCRETE;
            case 7: return Material.PINK_CONCRETE;
            case 8: return Material.MAGENTA_CONCRETE;
            case 9: return Material.GRAY_CONCRETE;
        }
    }
}
