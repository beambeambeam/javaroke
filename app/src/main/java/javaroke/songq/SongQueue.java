package javaroke.songq;

import javaroke.queue.QueueSong;
import javaroke.stack.StackSong;
import javaroke.models.NodeSong;

public class SongQueue{

    private QueueSong queue;
    private StackSong history;
    private NodeSong currentSong;

    public SongQueue(){
        this.queue = new QueueSong();
        this.history = new StackSong();
        this.currentSong = null;
    }

    //Add song to queue (get only songID)
    public void addSong(String songId){
        NodeSong newSong = new NodeSong(songId, "Unknown Title", "Unknown Artist", 0);
        queue.enqueue(newSong);
    }

    //Remove song from queue and put in history(stack)
    public NodeSong removeSong(){
        NodeSong song = queue.dequeue();
        if(song != null){
            history.push(song.getSongId(), song.getTitle(), song.getArtist(), song.getDuration());
            currentSong = queue.peek();
        }
        return song;
    }

    //Return the currently playing song
    public NodeSong getCurrentSong(){
        return currentSong;
    }

    //Show history of song that have been played (stack)
    public void historySong() {
        System.out.println("Play History:");
        if(history.isEmpty()){
            System.out.println("No songs played yet.");
        }else{
            StackSong tempStack = new StackSong();
            while(!history.isEmpty()){
                NodeSong song = history.pop();
                tempStack.push(song.getSongId(), song.getTitle(), song.getArtist(), song.getDuration());
            }
            while(!tempStack.isEmpty()){
                NodeSong song = tempStack.pop();
                System.out.println("- " + song.getSongId());
                history.push(song.getSongId(), song.getTitle(), song.getArtist(), song.getDuration());
            }
        }
    }
    
    //Show all songs in queue
    public void displayQueue(){
        System.out.println("Song Queue:");
        if(queue.isEmpty()) {
            System.out.println("Queue is empty.");
        }else{
            for(NodeSong song : queue){
                System.out.println("- " + song.getSongId());
            }
        }
    }
}