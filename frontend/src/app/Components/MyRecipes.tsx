'use client';

import React, { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import { fetchAuthenticatedUserDetails } from '@/services/userService';
import RecipeCard from './recipeCard';
import { RecipeInfo, AuthenticatedUserProfileInfo } from '@/outputTypes/types';

const MyRecipes: React.FC = () => {
    const [recipes, setRecipes] = useState<RecipeInfo[]>([]);
    const [loading, setLoading] = useState(true);
    const router = useRouter();

    useEffect(() => {
        const fetchData = async () => {
            const userDetails = await fetchAuthenticatedUserDetails('profile') as AuthenticatedUserProfileInfo;

            if (!userDetails) {
                router.push('/home');
                return;
            }

            setRecipes(userDetails.recipes);
            setLoading(false);
        };

        fetchData();
    }, [router]);

    const handleCreateNewRecipe = () => {
        router.push('/addrecipe');
    };

    const handleTagClick = (tag: string) => {
        router.push(`/search?tags=${tag}`);
    };

    if (loading) {
        return <div>Loading...</div>;
    }

    return (
        <div className="container mx-auto p-4">
            <div className="flex justify-between items-center mb-4">
                <h2 className="text-2xl font-semibold">My Recipes</h2>
                <button
                    className="bg-teal-600 hover:bg-teal-700 text-white font-bold py-2 px-4 rounded-lg"
                    onClick={handleCreateNewRecipe}
                >
                    Create a new recipe
                </button>
            </div>
            <div className="grid grid-cols-1 gap-4 md:grid-cols-2 lg:grid-cols-3">
                {recipes.map((recipe) => (
                    <RecipeCard key={recipe.id} recipe={recipe} showEditButton onTagClick={handleTagClick} />
                ))}
            </div>
        </div>
    );
};

export default MyRecipes;
