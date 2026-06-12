# How to Configure Locked Recipes

To configure this plugin, go to your server directory, then go to: \
    /plugins/LockedCrafting/config.yml

In the config.yml file, there is a section called _recipes_. This is how you change recipe requirements.

### Recipes

Each recipe takes two layers of YAML code.
The first layer is the namespace of the recipe. In most cases, this namespace is _minecraft_, 
so if this is the case you can omit the namespace and just use the recipe name (as shown in the diamond_spear recipe in the default).
> ##### How to find recipe names
> 
> When you put a recipe in the crafting grid, the logger will say: \
> _Crafting recipe namespace:key_ \
> You can use this to find the name of a recipe

If the namespace isn't minecraft, the outer layer is the namespace and the inner layer is the key, also known as the name. 
You can do this if the namespace is minecraft, as well, as shown by the mace recipe in the default.

### Requirements

Under a recipe, there is a list called _itemReqs_. 
Each element in the array corresponds to an item you need to have in order to craft this recipe.
Lists can be formatted in square brackets, like the diamond_spear example, or with dashes, like the mace example.