package com.fmning.service.manager;

import java.util.List;

import com.fmning.service.domain.SurvivalGuide;
import com.fmning.service.exceptions.NotFoundException;

public interface SGManager {
	
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
	 * @return a list of child articles, or empty list if not found
	 */
	public List<SurvivalGuide> getChildArticles(int parentId);
}
