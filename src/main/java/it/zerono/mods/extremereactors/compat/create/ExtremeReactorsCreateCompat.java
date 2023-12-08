/*
 *
 * ExtremeReactorsCreateCompat.java
 *
 * This file is part of Extreme Reactors Create Compat by ZeroNoRyouki, a Minecraft mod.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 *
 * DO NOT REMOVE OR EDIT THIS HEADER
 *
 */

package it.zerono.mods.extremereactors.compat.create;

import it.zerono.mods.extremereactors.compat.create.gamecontent.CreateContent;
import it.zerono.mods.zerocore.lib.data.ResourceLocationBuilder;
import net.minecraftforge.fml.common.Mod;

@Mod(value = ExtremeReactorsCreateCompat.MOD_ID)
public class ExtremeReactorsCreateCompat {

    public static final String MOD_ID = "er2create";
    public static final String MOD_NAME = "Extreme Reactors Create Compat";
    public static ResourceLocationBuilder ROOT_LOCATION = ResourceLocationBuilder.of(MOD_ID);

    public static final String NOT_AVAILABLE_TEXT = "?";

    public ExtremeReactorsCreateCompat() {


        Log.LOGGER.info(Log.COMPAT_CREATE, MOD_NAME + " starting....");

        CreateContent.initialize();
    }

}
