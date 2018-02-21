package me.ByteCoder.Core.Command.Commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.ByteCoder.Core.Main;
import me.ByteCoder.Core.Command.Command;
import me.ByteCoder.Core.Command.CommandSender;
import me.ByteCoder.Core.Logger.Logger;
import me.ByteCoder.Core.Logger.Logger.LoggType;
import me.ByteCoder.Core.Module.Module;
import me.ByteCoder.Core.Utils.TableGenerator;

public class CModule extends Command {

	@Override
	public void exec(CommandSender sender, String in, String[] args) {
		if(args.length == 1){
			sender.sendMessage("");
			sender.sendMessage(Main.getPrefix() + " Module page");
			sender.sendMessage("");
			sender.sendMessage(" * Load module - module load (Module name)");
			sender.sendMessage(" * Unload module - module unload (Module name)");
			sender.sendMessage(" * Information about module - module info (Module name)");
			sender.sendMessage(" * Modules list - module list");
			sender.sendMessage("");
		}else{
			if(args[1].equalsIgnoreCase("list")){
				if(Main.getModuleManager().getModules().isEmpty()){
					sender.sendMessage("");
					sender.sendMessage(" * No one module are currently loaded");
					sender.sendMessage("");
				}else{
					TableGenerator table = new TableGenerator();
					List<String> tableNames = Arrays.asList("ID", "NAME", "AUTHOR", "VERSION");
					List<List<String>> tableEntitys = new ArrayList<List<String>>();
					int i = 0;
					for(Module m : Main.getModuleManager().getModules()) {
						i++;
						List<String> l = new ArrayList<String>();
						l.add(String.valueOf(i));
						l.add(m.getDescription().getName());
						l.add(m.getDescription().getAuthor());
						l.add(m.getDescription().getVersion());
						tableEntitys.add(l);
					}
					System.out.println(table.generateTable(tableNames, tableEntitys, 1));
				}
			}else if(args[1].equalsIgnoreCase("load")){
				if(args.length == 2){
					sender.sendMessage(Main.getPrefix() + " Load module - module load (Module name)");
				}else{
					if(Main.getModuleManager().getModule(args[2]) == null){
						sender.sendMessage(Main.getPrefix() + " Could not load " + args[2] + " module.");
					}else{
						sender.sendMessage(Main.getPrefix() +  " Currently this command unavailable");
					}
				}
			}else if(args[1].equalsIgnoreCase("unload")) {
				if(args.length == 2) {
					sender.sendMessage(Main.getPrefix() + " Unload module - module unload (Module name)");
				}else {
					if(Main.getModuleManager().getModule(args[2]) == null){
						sender.sendMessage(Main.getPrefix() + " Could not find " + args[2] + " module.");
					}else{
						Main.getModuleManager().getModule(args[2]).onDisable();
					}
				}
			}else if(args[1].equalsIgnoreCase("info")){
				if(args.length == 2){
					sender.sendMessage(Main.getPrefix() + " Information about module - module info (Module name)");
				}else{
					Module module = Main.getModuleManager().getModule(args[2]);
					if(module != null){
						sender.sendMessage("");
						sender.sendMessage(Main.getPrefix() + " Module page - Information about " + module.getDescription().getName());
						sender.sendMessage(" * Author: " + module.getDescription().getAuthor());
						sender.sendMessage(" * Version: " + module.getDescription().getVersion());
						sender.sendMessage(" * Description: " + module.getDescription().getDescription());
						sender.sendMessage(" * Events: " + module.getEvents().size());
						if(module.getCommands().size() > 0) {
							sender.sendMessage(" * Commands: " + module.getCommands().size());
							for(Command c : module.getCommands()) {
								sender.sendMessage("  - " + c.getCommand() + " | " + c.getDescription());
							}
						}else {
							sender.sendMessage(" * Commands: 0");
						}
						sender.sendMessage("");
					}else{
						sender.sendMessage(Main.getPrefix() + " Could not find " + args[2] + " module.");
					}
				}
			}
		}
	}

	@Override
	public String getCommand() {
		return "module";
	}

	@Override
	public String getDescription() {
		return "Module control";
	}

}
