'use client';
import React from 'react';
import { RecipeInfo } from '@/outputTypes/types';
import RecipeCard from './recipeCard';
import { useRouter } from 'next/navigation';

interface UserRecipesProps {
    recipes: RecipeInfo[];
}

const UserRecipes: React.FC<UserRecipesProps> = ({ recipes }) => {
    const router = useRouter();

    const handleTagClick = (tag: string) => {
        router.push(`/search?tags=${tag}`);
    };

    return (
        <div className="grid grid-cols-1 gap-4 md:grid-cols-2 lg:grid-cols-3">
            {recipes.map((recipe) => (
                <RecipeCard key={recipe.id} recipe={recipe} onTagClick={handleTagClick} />
            ))}
        </div>
    );
};

export default UserRecipes;
