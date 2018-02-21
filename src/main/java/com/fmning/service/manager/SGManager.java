package com.fmning.service.manager;

import java.util.List;

import com.fmning.service.domain.SurvivalGuide;
import com.fmning.service.domain.SurvivalGuideHist;
import com.fmning.service.exceptions.NotFoundException;
import com.fmning.util.HistType;

public interface SGManager {
	
	/**
	 * Create a SG object and save into database
	 * @param title
	 * @param content
	 * @param parentId
	 * @param position
	 * @param ownerId
	 * @return the id of the new row
	 */
	public int createSG(String title, String content, int parentId, int position, int ownerId);
	
	/**
	 * Update SG if the input value is not null
	 * @param sgId
	 * @param title
	 * @param content
	 * @param parentId
	 * @param position
	 * @param updatedBy
	 * @return
	 * @throws NotFoundException
	 */
	public void softUpdateSG(int sgId, String title, String content, int parentId, int position, int updatedBy) throws NotFoundException;
	
	/**
	 * Get survival guide article by id
	 * @param sgId the id of the article
	 * @return the article
	 * @throws NotFoundException if no article exists under this id
	 */
	public SurvivalGuide getArticleById(int sgId) throws NotFoundException;
	
	/**
	 * Get a list of articles under a parent article, ordered by position
	 * @param parentId the nullable parent id
	 * @param returnContent if set to true, content will also be returned. By default, it's set to false
	 * @return a list of child articles, or empty list if not found
	 */
	public List<SurvivalGuide> getChildArticles(int parentId);
	public List<SurvivalGuide> getChildArticles(int parentId, boolean returnContent);
	
	/**
	 * Search survival guide articles by keyword
	 * @param keyword the searching keyword
	 * @return a list of search results match the criteria
	 * @throws NotFoundException if no articles found
	 */
	public List<SurvivalGuide> searchArticle(String keyword) throws NotFoundException;
	
	/**
	 * Get sg history by the history id
	 * @param histId the history id
	 * @return the history object
	 * @throws NotFoundException of such id does not exist
	 */
	public SurvivalGuideHist getEdintingHistorybyId(int histId) throws NotFoundException;
	
	/**
	 * Get a list of SG from history table with action U as update. Location only updates are ignored here
	 * @param sgId the id of the article
	 * @param returnContent if the sg content will be returned or not.
	 * @param type the type of history
	 * @param userId the owner of history
	 * @return a list of SG history
	 * @throws NotFoundException if no history found
	 */
	public List<SurvivalGuideHist> getEditingHistory(int sgId, boolean returnContent, HistType type, int userId) throws NotFoundException;
}
