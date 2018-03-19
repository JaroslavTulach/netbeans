/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package javax.help;

import javax.help.event.*;

/**
 * The interface to a HelpModel that manipulates text.
 *
 * It provides additional text operations.
 */

public interface TextHelpModel extends HelpModel {
    /**
     * Gets the title of the document.
     *
     * @return The title of document visited.
     */
    public String getDocumentTitle();

    /**
     * Sets the title of the document.
     * A property change event is generated.
     *
     * @param title The title currently shown.
     */
    public void setDocumentTitle(String title);


    /**
     * Removes all highlights on the current document.
     */
    public void removeAllHighlights();

    /**
     * Adds a highlight to a range of positions in a document.
     *
     * @param pos0 Start position.
     * @param pos1 End position.
     */
    public void addHighlight(int pos0, int pos1);

    /**
     * Sets the highlights to be a range of positions in a document.
     *
     * @param h The array of highlight objects.
     */
    public void setHighlights(Highlight[] h);

    /**
     * Gets all highlights.
     */
    public Highlight[] getHighlights();

    /**
     * Adds a listener for a TextHelpModel.
     */
    public void addTextHelpModelListener(TextHelpModelListener l);

    /**
     * Removes a listener for a TextHelpModel.
     */
    public void removeTextHelpModelListener(TextHelpModelListener l);

    /**
     * This is very similar to javax.swing.text.Highlighter.Highlight
     * except that it does not use the notion of HighlightPainter.
     */
    public interface Highlight {
	/**
	 * Gets the starting model offset of the highlight.
	 *
	 * @return The starting offset >= 0.
	 */
	public int getStartOffset();

	/**
	 * Gets the ending model offset of the highlight.
	 *
	 * @return The ending offset >= 0.
	 */
	public int getEndOffset();
    }
}
