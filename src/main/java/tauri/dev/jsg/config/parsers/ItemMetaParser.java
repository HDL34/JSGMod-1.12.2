package tauri.dev.jsg.config.parsers;

import tauri.dev.jsg.util.ItemMetaPair;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ItemMetaParser {
	
	/**
	 * Parses array of configured items/blocks.
	 * 
	 * @param config Array of single lines
	 * @return List of {@link IBlockState}s or empty list.
	 */
	@Nonnull
	public static List<ItemMetaPair> parseConfig(String[] config) {
		List<ItemMetaPair> list = new ArrayList<>();

		for (String line : config) {
			ItemMetaPair stack = getItemMetaPairFromString(line);
			
			if(stack != null) {
				list.add(stack);
			}
		}
		
		return list;
	}
	
	/**
	 * Parses single line of the config.
	 * 
	 * @param line Consists of modid:blockid[:meta]
	 * @return {@link IBlockState} when valid block, {@code null} otherwise.
	 */
	@Nullable
	public static ItemMetaPair getItemMetaPairFromString(String line) {
        String[] parts = line.trim().split(":", 3);
        
        Item item = Item.REGISTRY.getObject(new ResourceLocation(parts[0], parts[1]));

        if (item != null) {
        	if (parts.length == 2 || parts[2].equals("*"))
        		return new ItemMetaPair(item, 0);
        	
            try {
            	return new ItemMetaPair(item, Integer.parseInt(parts[2]));
            }
            
        	catch (NumberFormatException e) {
    			return null;
    		}
        }
        
        return null;
    }
}
