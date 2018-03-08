package Models;

import SupportClasses.Subject;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Product extends Subject{

    private int _id;
    private String _title;
    private ArrayList<String> _trackList;
    private String _coverImage;
    private float _price;
    private LocalDateTime _firstAddedInStore;
    private String _description;
    private Musician _artist;
    private String _genre;
    private ArrayList<Musician> _involvedArtists;
    private ArrayList<String> _usedInstruments;
    private int _productStocks;

    public Product(int id, String title){
        _id = id;
        _title = title;
    }

    public Product (int id, String title, ArrayList<String> trackList, String coverImage, float price, LocalDateTime firstAddedInStore, String description, Musician artist, String genre, ArrayList<Musician> involvedArtists, ArrayList<String> usedInstruments, int productStocks) {
        _id = id;
        _title = title;
        _trackList = trackList;
        _coverImage = coverImage;
        _price = price;
        _firstAddedInStore = firstAddedInStore;
        _description=description;
        _artist = artist;
        _genre = genre;
        _involvedArtists = involvedArtists;
        _usedInstruments = usedInstruments;
        _productStocks = productStocks;
    }

    public void set_code(int id) {
        this._id = id;
        notifyAllObservers();
    }

    public void set_title(String title) {
        this._title = title;
        notifyAllObservers();
    }

    public void set_trackList(ArrayList<String> songList) {
        this._trackList = songList;
        notifyAllObservers();
    }

    public void set_coverImage(String coverImage) {
        this._coverImage = coverImage;
        notifyAllObservers();
    }

    public void set_price(float price) {
        this._price = price;
        notifyAllObservers();
    }

    public void set_firstAddedInStore(LocalDateTime firstAddedInStore) {
        this._firstAddedInStore = firstAddedInStore;
        notifyAllObservers();
    }

    public void set_artist(Musician artist) {
        this._artist = artist;
        notifyAllObservers();
    }

    public void set_description(String description) {
        this._description = description;
        notifyAllObservers();
    }

    public void set_genre(String genre) {
        this._genre = genre;
        notifyAllObservers();
    }

    public void set_involvedArtists(ArrayList<Musician> involvedArtists) {
        this._involvedArtists = involvedArtists;
        notifyAllObservers();
    }

    public void set_usedInstruments(ArrayList<String> usedInstruments) {
        this._usedInstruments = usedInstruments;
        notifyAllObservers();
    }

    public void set_productStocks(int productStocks) {
        this._productStocks = productStocks;
        notifyAllObservers();
    }

    public int get_id() {
        return _id;
    }

    public String get_title() {
        return _title;
    }

    public ArrayList<String> get_songList() {
        return _trackList;
    }

    public String get_coverImage() {
        return _coverImage;
    }

    public float get_price() {
        return _price;
    }

    public LocalDateTime get_firstAddedInStore() {
        return _firstAddedInStore;
    }

    public Musician get_artist() {
        return _artist;
    }

    public String get_description() {
        return _description;
    }

    public String get_genre() {
        return _genre;
    }

    public ArrayList<Musician> get_involvedArtists() {
        return _involvedArtists;
    }

    public ArrayList<String> get_usedInstruments() {
        return _usedInstruments;
    }

    public int get_productStocks() {
        return _productStocks;
    }
}