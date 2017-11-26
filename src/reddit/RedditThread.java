package reddit;

import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import javafx.scene.image.Image;
/**
 * RedditThread parses the JSON obtained from the GET call
 * 
 * @author Devin Nguyen
 *
 */
public class RedditThread {

	private String domain;
	private String subreddit;
	private String title;
	private Image thumbnail;
	private int score;
	private boolean over_18;
	private String permalink;
	private URL url;
	private String selftext_html;
	private String selftext;
	private String author;
	private int num_comments;
	
	public RedditThread(JSONObject jsonObject) {
		try {
			this.setDomain(jsonObject.getJSONObject("data").getString("domain"));
			this.setSubreddit(jsonObject.getJSONObject("data").getString("subreddit_name_prefixed"));
			this.setTitle(jsonObject.getJSONObject("data").getString("title"));
			/*
			try {
				this.setThumbnail(new Image(jsonObject.getJSONObject("data").getString("thumbnail")));
			} catch (IllegalArgumentException e) {
				this.setThumbnail(new Image(jsonObject.getJSONObject("data")
						.getJSONObject("preview").getJSONArray("images")
						.getJSONObject(0).getJSONObject("source")
						.getString("url")));
			}
			*/
			this.setScore(jsonObject.getJSONObject("data").getInt("score"));
			this.setOver_18(jsonObject.getJSONObject("data").getBoolean("over_18"));
			if(this.isOver_18())
				this.setThumbnail(new Image("https://upload.wikimedia.org/wikipedia/commons/c/c4/No_icon_red.svg"));
			this.setPermalink(jsonObject.getJSONObject("data").getString("permalink"));
			this.setUrl(new URL(jsonObject.getJSONObject("data").getString("url")));
			//this.setSelftext_html(jsonObject.getJSONObject("data").getString("selftext_html"));
			this.setSelftext(jsonObject.getJSONObject("data").getString("selftext"));
			this.setAuthor(jsonObject.getJSONObject("data").getString("author"));
			this.setNum_comments(jsonObject.getJSONObject("data").getInt("num_comments"));
		} catch (JSONException e) {
			//this.setSelftext_html("null");
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		};
	}
	
	/**
	 * @return the domain
	 */
	public String getDomain() {
		return domain;
	}
	/**
	 * @param domain the domain to set
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}
	/**
	 * @return the subreddit
	 */
	public String getSubreddit() {
		return subreddit;
	}
	/**
	 * @param subreddit the subreddit to set
	 */
	public void setSubreddit(String subreddit) {
		this.subreddit = subreddit;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the thumbnail
	 */
	public Image getThumbnail() {
		return thumbnail;
	}
	/**
	 * @param thumbnail the thumbnail to set
	 */
	public void setThumbnail(Image thumbnail) {
		this.thumbnail = thumbnail;
	}
	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}
	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}
	/**
	 * @return the over_18
	 */
	public boolean isOver_18() {
		return over_18;
	}
	/**
	 * @param over_18 the over_18 to set
	 */
	public void setOver_18(boolean over_18) {
		this.over_18 = over_18;
	}
	/**
	 * @return the permalink
	 */
	public String getPermalink() {
		return permalink;
	}
	/**
	 * @param permalink the permalink to set
	 */
	public void setPermalink(String permalink) {
		this.permalink = permalink;
	}
	/**
	 * @return the url
	 */
	public URL getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(URL url) {
		this.url = url;
	}
	/**
	 * @return the selftext_html
	 */
	public String getSelftext_html() {
		return selftext_html;
	}
	/**
	 * @param selftext_html the selftext_html to set
	 */
	public void setSelftext_html(String selftext_html) {
		this.selftext_html = selftext_html;
	}
	/**
	 * @return the selftext
	 */
	public String getSelftext() {
		return selftext;
	}
	/**
	 * @param selftext the selftext to set
	 */
	public void setSelftext(String selftext) {
		this.selftext = selftext;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return the num_comments
	 */
	public int getNum_comments() {
		return num_comments;
	}

	/**
	 * @param num_comments the num_comments to set
	 */
	public void setNum_comments(int num_comments) {
		this.num_comments = num_comments;
	}
	
	
	
}
