package com.efnilite.redaktor.command;

import com.efnilite.redaktor.command.util.Command;
import com.efnilite.redaktor.command.util.Commandable;
import com.efnilite.redaktor.pattern.Pattern;
import com.efnilite.redaktor.player.BukkitPlayer;
import com.efnilite.redaktor.selection.CuboidSelection;
import com.efnilite.redaktor.util.Util;
import com.efnilite.redaktor.wrapper.RedaktorPlayer;
import org.bukkit.Location;
import org.bukkit.block.data.BlockData;

import java.util.ArrayList;
import java.util.List;

public class EditorCommands implements Commandable {

    private Pattern.Parser parser;

    public EditorCommands() {
        this.parser = new Pattern.Parser();
    }

    @Command(permission = "redaktor.replace", usage = "/replace <target> <replacement>")
    public void replace(RedaktorPlayer<?> sender, String[] args) {
        if (args.length == 2) {
            if (sender.getPos1() != null && sender.getPos2() != null) {
                CuboidSelection selection = new CuboidSelection(sender.getPos1(), sender.getPos2());
                String[] find = args[0].split(",");
                List<BlockData> data = new ArrayList<>();

                for (String pattern : find) {
                    BlockData current = Pattern.parseData(pattern);

                    if (current == null) {
                        sender.send("&cInvalid pattern! Please check if you typed every correctly.");
                        return;
                    }

                    data.add(current);
                }

                Pattern replace = parser.parse(args[1]);

                if (replace == null) {
                    sender.send("&cInvalid pattern! Please check if you typed every correctly.");
                    return;
                }

                sender.getEditor().replace(selection, data, replace);
            } else {
                sender.send("You need to set position 1 and 2 to be able to do that!");
            }
        } else {
            sender.send("You need to set a target and a replacement pattern!");
        }
    }

    @Command(permission = "redaktor.replaceall", usage = "/replaceall <radius> <target> <replacement>")
    public void replaceall(RedaktorPlayer<?> sender, String[] args) {
        if (args.length == 3) {
            if (sender instanceof BukkitPlayer) {
                String[] find = args[1].split(",");
                List<BlockData> data = new ArrayList<>();

                for (String pattern : find) {
                    BlockData current = Pattern.parseData(pattern);

                    if (current == null) {
                        sender.send("&cInvalid pattern! Please check if you typed every correctly.");
                        return;
                    }

                    data.add(current);
                }

                Pattern replace = parser.parse(args[2]);

                if (replace == null) {
                    sender.send("&cInvalid pattern! Please check if you typed every correctly.");
                    return;
                }

                sender.getEditor().replaceAll(sender.getLocation(), Integer.parseInt(args[0]), data, replace);
            } else {
                sender.send("Only players can use this command.");
            }
        } else {
            sender.send("You need to set a target, a radius and a replacement pattern!");
        }
    }

    @Command(permission = "redaktor.pos1", description = "Set position 1 of your selection to a location.",
            usage = "/pos1 [location] (example: /pos1 43,5,76)", aliases = { "position1" })
    public void pos1(RedaktorPlayer<?> sender, String[] args) {
        Location location;
        if (args.length == 0) {
            location = sender.getLocation();
        } else {
            location = Util.fromString(args[0]);
        }
        sender.setPos1(location);
        if (sender.getPos1() != null && sender.getPos2() != null) {
            CuboidSelection selection = new CuboidSelection(sender.getPos1(), sender.getPos2());
            sender.send("&7Position 1 set to " + Util.toString(location) + " (" + selection.getDimensions().getVolume() + " blocks)");
        } else {
            sender.send("&7Position 1 set to " + Util.toString(location) + " (0 blocks)");
        }
    }

    @Command(permission = "redaktor.pos2", description = "Set position 2 of your selection to a location.",
            usage = "/pos2 [location] (example: /pos1 65,54,-32)", aliases = { "position2" })
    public void pos2(RedaktorPlayer<?> sender, String[] args) {
        Location location;
        if (args.length == 0) {
            location = sender.getLocation();
        } else {
            location = Util.fromString(args[0]);
        }
        sender.setPos2(location);
        if (sender.getPos1() != null && sender.getPos2() != null) {
            CuboidSelection selection = new CuboidSelection(sender.getPos1(), sender.getPos2());
            sender.send("&7Position 2 set to " + Util.toString(location) + " (" + selection.getDimensions().getVolume() + " blocks)");
        } else {
            sender.send("&7Position 2 set to " + Util.toString(location) + " (0 blocks)");
        }
    }

    @Command(permission = "redaktor.set", usage = "/set <pattern>")
    public void set(RedaktorPlayer<?> sender, String[] args) {
        if (args.length == 1) {
            if (sender.getPos1() != null && sender.getPos2() != null) {
                CuboidSelection selection = new CuboidSelection(sender.getPos1(), sender.getPos2());
                Pattern pattern = parser.parse(args[0]);

                if (pattern == null) {
                    sender.send("&cInvalid pattern! Please check if you typed every correctly.");
                    return;
                }

                sender.getEditor().setBlocks(selection, pattern);
            } else {
                sender.send("You need to set position 1 and 2 to be able to do that!");
            }
        } else {
            sender.send("You need to set a pattern!");
        }
    }

    @Command(permission = "redaktor.slow", usage = "/slow <pattern> <amount of blocks per tick (1/20th of a second)>")
    public void slow(RedaktorPlayer<?> sender, String[] args) {
        if (args.length == 2) {
            if (sender.getPos1() != null && sender.getPos2() != null) {
                CuboidSelection selection = new CuboidSelection(sender.getPos1(), sender.getPos2());
                Pattern pattern = parser.parse(args[0]);

                if (pattern == null) {
                    sender.send("&cInvalid pattern! Please check if you typed every correctly.");
                    return;
                }

                sender.getEditor().setSlowBlocks(selection, pattern, Integer.parseInt(args[1]));
            } else {
                sender.send("You need to set position 1 and 2 to be able to do that!");
            }
        } else {
            sender.send("You need to set a pattern!");
        }
    }

    @Command(permission = "redaktor.copy", usage = "/copy <x> [y] <z>")
    public void copy(RedaktorPlayer<?> sender, String[] args) {
        if (args.length == 3 || args.length == 2) {
            CuboidSelection selection = new CuboidSelection(sender.getPos1(), sender.getPos2());
            if (args.length == 3) {
                sender.getEditor().copyCuboid(selection, Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
            } else {
                sender.getEditor().copyCuboid(selection, Integer.parseInt(args[0]), Integer.parseInt(args[1]));
            }
        }
    }

    @Command(permission = "redaktor.ch", usage = "/ch", aliases = { "ch" })
    public void clearhistory(RedaktorPlayer<?> sender, String[] args) {
        sender.getEditor().clearHistory();
    }

    @Command(permission = "redaktor.undo", usage = "/undo [amount]")
    public void undo(RedaktorPlayer<?> sender, String[] args) {
        if (args.length == 1) {
            sender.getEditor().undo(Integer.parseInt(args[0]));
        } else {
            sender.getEditor().undo();
        }
    }

    @Command(permission = "redaktor.undo", usage = "/redo [amount]")
    public void redo(RedaktorPlayer<?> sender, String[] args) {
        if (args.length == 1) {
            sender.getEditor().redo(Integer.parseInt(args[0]));
        } else {
            sender.getEditor().redo();
        }
    }
}