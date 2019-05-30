/*******************************************************************************
 * Aedes Alert, Support to collect data to combat dengue
 * Copyright (C) 2017 Fundación Anesvad
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.gruposca.sapev.api.helper;

import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import com.sun.jersey.core.util.Base64;

public class ImageHelper {

	public static BufferedImage decodeToImage(String imageString) {

		 BufferedImage image = null;
	        byte[] imageByte;
	        try {
	            imageByte = Base64.decode(imageString);
	            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
	            image = ImageIO.read(bis);
	            bis.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return image;
    }
	
	public static String encodeToString(BufferedImage image, String type) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, type, bos);
            byte[] imageBytes = bos.toByteArray();
            imageString =  new String(Base64.encode(imageBytes), "UTF-8");
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageString;
    }	
}
