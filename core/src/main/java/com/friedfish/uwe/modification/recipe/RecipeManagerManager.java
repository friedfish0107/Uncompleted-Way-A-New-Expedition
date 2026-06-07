package com.friedfish.uwe.modification.recipe;

import com.mojang.logging.LogUtils;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.event.RecipesUpdatedEvent;
import net.neoforged.neoforge.event.AddReloadListenerEvent;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber
public class RecipeManagerManager {
    @SubscribeEvent
    public static void onAddReloadListener(AddReloadListenerEvent event) throws IOException {

    }
    @SubscribeEvent
    public static void onRecipesUpdated(RecipesUpdatedEvent event) throws IOException {
        if(!FMLEnvironment.production) {// 仅开发环境
            RecipeManager recipeManager = event.getRecipeManager();
            RegistryAccess registryAccess=Minecraft.getInstance().level.registryAccess();

            Path path = Minecraft.getInstance().gameDirectory.toPath().resolve("debug");

            List<ResourceLocation> toRemove=new RemoveRecipeListBuilder(recipeManager,registryAccess)
                    .remove(ItemRegistry.BOOK_STACK_BLOCK_ITEM.getId())
                    .remove(ItemRegistry.INSCRIPTION_TABLE_BLOCK_ITEM.getId())
                    .build();

            for (ResourceLocation resource:toRemove){
                writeStringToFile(path.resolve(resource.getNamespace()+"\\recipe"),resource.getPath()+".json","{\"neoforge:conditions\":[{\"type\":\"neoforge:false\"}]}");
            }
        }
    }
    protected static void writeStringToFile(Path path,String fileName,String content) throws IOException {

        if(Files.notExists(path))
            Files.createDirectories(path);

        LogUtils.getLogger().debug("addingFile:{},{},content{}", path,fileName,content);

        Files.writeString(path.resolve(fileName),content, StandardCharsets.UTF_8);
    }
    static class RemoveRecipeListBuilder{
        private List<ResourceLocation> toRemove;
        private final RecipeManager recipeManager;
        private final RegistryAccess registryAccess;
        public RemoveRecipeListBuilder(RecipeManager recipeManager,RegistryAccess registryAccess){
            this.toRemove=new ArrayList<>();
            this.recipeManager=recipeManager;
            this.registryAccess=registryAccess;

        }
        public RemoveRecipeListBuilder removeOutput(ItemLike item){
            for (RecipeHolder<?> recipe:recipeManager.getRecipes()){
                if(recipe.value().getResultItem(registryAccess).is(item.asItem())){
                    this.toRemove.add(recipe.id());
                };
            }
            return this;
        }
        public RemoveRecipeListBuilder remove(ResourceLocation recipeId){
            this.toRemove.add(recipeId);
            return this;
        }
        public RemoveRecipeListBuilder remove(String modId,String recipeId){
            this.toRemove.add(ResourceLocation.fromNamespaceAndPath(modId,recipeId));
            return this;
        }
        public List<ResourceLocation> build(){
            return toRemove;
        }
    }
}
