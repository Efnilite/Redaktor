package dev.efnilite.redaktor.command;

import dev.efnilite.redaktor.command.util.Command;
import dev.efnilite.redaktor.command.util.Commandable;
import dev.efnilite.redaktor.pattern.Pattern;
import dev.efnilite.redaktor.player.BukkitPlayer;
import dev.efnilite.redaktor.selection.CuboidSelection;
import dev.efnilite.redaktor.selection.Selection;
import dev.efnilite.redaktor.util.Util;
import dev.efnilite.redaktor.wrapper.RedaktorPlayer;
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
                        sender.sendLang("invalid-pattern");
                        return;
                    }

                    data.add(current);
                }

                Pattern replace = parser.parse(args[1]);

                if (replace == null) {
                    sender.sendLang("invalid-pattern");
                    return;
                }

                sender.getEditor().replace(selection, data, replace);
            } else {
                sender.sendLang("set-positions");
            }
        } else {
            sender.sendLang("set-target");
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
                        sender.sendLang("invalid-pattern");
                        return;
                    }

                    data.add(current);
                }

                Pattern replace = parser.parse(args[2]);

                if (replace == null) {
                    sender.sendLang("invalid-pattern");
                    return;
                }

                sender.getEditor().replaceAll(sender.getLocation(), Integer.parseInt(args[0]), data, replace);
            } else {
                sender.sendLang("only-players");
            }
        } else {
            sender.sendLang("set-target");
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
            sender.setSelection(selection);

            sender.sendLang("set-position-1", Util.toString(location), Integer.toString(selection.getDimensions().getVolume()));
        } else {
            sender.sendLang("set-position-1", Util.toString(location), Integer.toString(0));
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
            sender.setSelection(selection);

            sender.sendLang("set-position-2", Util.toString(location), Integer.toString(selection.getDimensions().getVolume()));
        } else {
            sender.sendLang("set-position-2", Util.toString(location), Integer.toString(0));
        }
    }

    @Command(permission = "redaktor.set", usage = "/set <pattern>")
    public void set(RedaktorPlayer<?> sender, String[] args) {
        if (args.length == 1) {
            if (sender.getPos1() != null && sender.getPos2() != null) {
                Selection selection = sender.getSelection();
                Pattern pattern = parser.parse(args[0]);

                if (pattern == null) {
                    sender.sendLang("invalid-pattern");
                    return;
                }

                sender.getEditor().setBlocks(selection, pattern);
                sender.sendLang("set-blocks", Integer.toString(selection.getDimensions().getVolume()));
            } else {
                sender.sendLang("set-positions");
            }
        } else {
            sender.sendLang("set-pattern");
        }
    }

    @Command(permission = "redaktor.slow", usage = "/slow <pattern> <amount of blocks per tick (1/20th of a second)>")
    public void slow(RedaktorPlayer<?> sender, String[] args) {
        if (args.length == 2) {
            if (sender.getPos1() != null && sender.getPos2() != null) {
                CuboidSelection selection = new CuboidSelection(sender.getPos1(), sender.getPos2());
                Pattern pattern = parser.parse(args[0]);

                if (pattern == null) {
                    sender.sendLang("invalid-pattern");
                    return;
                }

                sender.getEditor().setSlowBlocks(selection, pattern, Integer.parseInt(args[1]));
                sender.sendLang("set-blocks", Integer.toString(selection.getDimensions().getVolume()));
            } else {
                sender.sendLang("set-positions");
            }
        } else {
            sender.sendLang("set-pattern");
        }
    }

    @Command(permission = "redaktor.ch", usage = "/ch", aliases = { "ch" })
    public void clearhistory(RedaktorPlayer<?> sender, String[] args) {
        sender.getEditor().clearHistory();
        sender.sendLang("clear-history");
    }

    @Command(permission = "redaktor.undo", usage = "/undo [amount]")
    public void undo(RedaktorPlayer<?> sender, String[] args) {
        if (args.length == 1) {
            sender.sendLang("undo", args[0]);
            sender.getEditor().undo(Integer.parseInt(args[0]));
        } else {
            sender.sendLang("undo", Integer.toString(1));
            sender.getEditor().undo();
        }
    }

    @Command(permission = "redaktor.undo", usage = "/redo [amount]")
    public void redo(RedaktorPlayer<?> sender, String[] args) {
        if (args.length == 1) {
            sender.sendLang("redo", args[0]);
            sender.getEditor().redo(Integer.parseInt(args[0]));
        } else {
            sender.sendLang("redo", Integer.toString(1));
            sender.getEditor().redo();
        }
    }
}