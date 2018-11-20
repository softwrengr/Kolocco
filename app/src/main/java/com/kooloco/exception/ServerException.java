/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kooloco.exception;

/**
 * Exception throw by the application when a there is a network connection exception.
 */
public class ServerException extends Exception {
  int serverCode;

  public ServerException() {
    super();
  }

  public ServerException(final String message, final int serverCode) {
    super(message);
    this.serverCode = serverCode;
  }

  public ServerException(final String message, final int serverCode, final Throwable cause) {
    super(message, cause);
    this.serverCode = serverCode;
  }

  public ServerException(final Throwable cause) {
    super(cause);
  }

  public int getServerCode() {
    return serverCode;
  }

  public void setServerCode(int serverCode) {
    this.serverCode = serverCode;
  }
}
