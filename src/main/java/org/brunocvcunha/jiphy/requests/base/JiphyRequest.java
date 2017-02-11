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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.client.ClientProtocolException;
import org.brunocvcunha.inutils4j.MyStreamUtils;
import org.brunocvcunha.jiphy.Jiphy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;

@AllArgsConstructor
@NoArgsConstructor
@Log4j
public abstract class JiphyRequest<T> {

    @Getter
    @Setter
    @JsonIgnore
    protected Jiphy api;

    /**
     * @return the url
     */
    public abstract String getUrl();

    /**
     * @return the method
     */
    public abstract String getMethod();

    /**
     * @return the payload
     */
    public String getPayload() {
        return null;
    }

    /**
     * @return the result
     * @throws IOException
     * @throws ClientProtocolException
     */
    public abstract T execute() throws ClientProtocolException, IOException;

    /**
     * Process response
     * 
     * @param resultCode
     *            Status Code
     * @param content
     *            Content
     */
    public abstract T parseResult(int resultCode, String content);

    /**
     * Parses Json into type
     * 
     * @param str
     *            Entity content
     * @param clazz
     *            Class
     * @return Result
     */
    @SneakyThrows
    public <U> U parseJson(String str, Class<U> clazz) {
        log.info("Reading " + clazz.getSimpleName() + " from " + str);
        return new ObjectMapper().readValue(str, clazz);
    }

    /**
     * Parses Json into type
     * 
     * @param is
     *            Entity stream
     * @param clazz
     *            Class
     * @return Result
     */
    @SneakyThrows
    public T parseJson(InputStream is, Class<T> clazz) {
        return this.parseJson(MyStreamUtils.readContent(is), clazz);
    }

}
