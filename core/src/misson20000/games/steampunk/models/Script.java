package misson20000.games.steampunk.models;

import misson20000.games.steampunk.interfaces.Scriptable;
import misson20000.games.steampunk.util.FireAndForget;
import misson20000.games.steampunk.util.Tokenizer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by misson20000 on 12/3/14.
 */
public class Script extends Model {
  public Map map;
  public java.util.Map<String, Scriptable> libraries;

  public Script(Tokenizer t, Map m) throws IOException {
    this.runningEvents = new ArrayList<Event>();
    this.libraries = new HashMap<String, Scriptable>();
    libraries.put("time", new Scriptable() {
      @Override
      public void addEvent(String hook, Event evt) {

      }

      @Override
      public boolean command(String cmd, final Event evt) {
        String parts[] = cmd.split(" ");
        if(parts[0].equals("sleep")) {
          evt.pause = true;
          FireAndForget.timer(Integer.parseInt(parts[1]), new Runnable() {
            @Override
            public void run() {
              evt.pause = false;
            }
          });
          return true;
        }
        return false;
      }
    });
    this.map = m;
    while(!t.atEnd) {
      String evtOwner = t.nextToken(".", false);
      String hook = t.nextToken("{", false);
      if(t.atEnd) { break; }
      hook = hook.trim();
      resolveScriptable(evtOwner).addEvent(hook, parseEvent(t));
    }
  }

  public Event parseEvent(Tokenizer t) throws IOException {
    Event evt = new Event(this);
    String cmd = t.nextToken(";}", false);
    while(!cmd.isEmpty()) {
      evt.commands.add(cmd);
      if(t.lastDelim == '}') {
        break;
      }
      cmd = t.nextToken(";}", false);
    }
    return evt;
  }

  public Scriptable resolveScriptable(String u) {
    if(u.equals("level")) {
      return map;
    }
    if(map.idMap.containsKey(u)) {
      return map.idMap.get(u);
    }
    if(libraries.containsKey(u)) {
      return libraries.get(u);
    }
    System.out.println("Could not resolve scriptable: " + u);
    return null;
  }

  public void update() {
    Iterator<Event> i = runningEvents.iterator();
    while(i.hasNext()) {
      if(i.next().run()) { i.remove(); }
    }
  }

  public List<Event> runningEvents;

  public class Event {
    private Script s;
    private int pc;
    public List<String> commands;
    public boolean pause;

    public Event(Script s) {
      this.s = s;
      this.commands = new ArrayList<String>();
    }

    public void execute() {
      runningEvents.add(this);
      this.pc = 0;
    }

    public void end() {
      this.pc = commands.size();
      runningEvents.remove(this);
    }

    protected boolean run() {
      if(pause) { return false; }
      while(this.pc < commands.size()) {
        String parts[] = commands.get(this.pc).split("\\.");
        this.pc++;
        if(s.resolveScriptable(parts[0]).command(parts[1], this)) {
          break;
        }
      }
      return this.pc == commands.size();
    }
  }
}
