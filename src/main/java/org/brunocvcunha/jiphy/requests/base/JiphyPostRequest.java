/**
 * Copyright (C) 2017 Bruno Candido Volpato da Cunha (brunocvcunha@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.brunocvcunha.jiphy.requests.base;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.brunocvcunha.jiphy.JiphyConstants;

import lombok.extern.log4j.Log4j;

/**
 * 
 * @author brunovolpato
 *
 */
@Log4j
public abstract class JiphyPostRequest<T> extends JiphyRequest<T> {

    @Override
    public String getMethod() {
        return "POST";
    }

    @Override
    public T execute() throws ClientProtocolException, IOException {
        HttpPost post = new HttpPost(JiphyConstants.API_URL + getUrl());
        post.addHeader("Connection", "close");
        post.addHeader("Accept", "*/*");
        post.addHeader("Content-Type", "application/json; charset=UTF-8");
        post.addHeader("Accept-Language", "en-US");

        post.setEntity(new StringEntity(getPayload()));

        HttpResponse response = api.getClient().execute(post);
        api.setLastResponse(response);

        int resultCode = response.getStatusLine().getStatusCode();
        String content = EntityUtils.toString(response.getEntity());

        post.releaseConnection();

        return parseResult(resultCode, content);
    }

}
