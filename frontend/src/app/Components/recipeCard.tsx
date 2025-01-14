import React from 'react';
import Image from 'next/image';
import { RecipeInfo, SearchResultRecipe } from '@/outputTypes/types';
import {router} from "next/client";

interface RecipeCardProps {
    recipe: RecipeInfo | SearchResultRecipe;
    showEditButton?: boolean;
    onTagClick?: (tag: string) => void;
}

const RecipeCard: React.FC<RecipeCardProps> = ({ recipe, showEditButton = false, onTagClick = () => {} }) => {
    return (
        <div className="max-w-sm bg-white border border-gray-200 rounded-lg shadow-lg overflow-hidden">
            <div className="relative h-48">
                <Image
                    src={recipe.imageURL || '/images/DefaultRecipe.jpg'}
                    alt={recipe.name}
                    layout="fill"
                    objectFit="cover"
                    className="rounded-t-lg"
                />
            </div>
            <div className="p-4">
                <h5 className="text-xl font-semibold text-gray-900">{recipe.name}</h5>
                <p className="mt-2 text-gray-600">{recipe.description}</p>
                <div className="flex mt-3">
                    {recipe.tags.map((tag, tagIdx) => (
                        <span
                            key={tagIdx}
                            className="inline-block bg-blue-100 text-teal-600 text-xs font-semibold px-2 py-1 mr-2 rounded-full cursor-pointer"
                            onClick={() => onTagClick(tag)}
                        >
                            {tag}
                        </span>
                    ))}
                </div>
                <div className="mt-4 flex items-center">
                    <svg className="w-4 h-4 text-yellow-500 mr-1" fill="currentColor" viewBox="0 0 20 20">
                        <path fillRule="evenodd" d="M10 0c-.553 0-1.068.19-1.477.508-.41.32-.703.762-.859 1.242-.156.48-.172 1.002-.048 1.493.125.492.442.916.859 1.243l3.772 2.93-1.477 5.684c-.066.254-.102.519-.102.785v.5c0 .414.157.802.439 1.096.283.294.664.458 1.061.458h.5c.415 0 .802-.157 1.096-.439.294-.283.458-.664.458-1.061v-.5c0-.266-.036-.531-.102-.785l-1.477-5.684 3.772-2.93c.416-.327.734-.75.859-1.243.124-.491.108-1.013-.048-1.493-.156-.48-.448-.922-.859-1.242C16.068.19 15.553 0 15 0h-5zm0 2.707L11.55 6.5H8.45L10 2.707zm.5 10.136v5.157l2.5 1.938V13.98h3.691l-2.954-2.291 1.05-4.054-3.787 2.939L10.5 7.843 9.19 6.57l1.05 4.054-3.787-2.938 1.05 4.054H5.5v6.761l2.5-1.938v-5.157L10 12.843z" clipRule="evenodd"></path>
                    </svg>
                    <p className="text-gray-700">{recipe.rating} ({recipe.reviews} reviews)</p>
                </div>
                {showEditButton && (
                    <button
                        className="mt-4 bg-teal-600 hover:bg-teal-700 text-white p-2 rounded-md"
                        onClick={() => router.push(`/recipe/edit/${recipe.id}`)}
                    >
                        Edit Recipe
                    </button>
                )}
                <a href="#" className="block mt-4 bg-teal-600 hover:bg-teal-600 text-white text-center py-2 px-4 rounded-lg transition duration-300 ease-in-out">View recipe</a>
            </div>
        </div>
    );
};

export default RecipeCard;
