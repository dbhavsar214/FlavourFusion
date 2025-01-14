// src/outputTypes/types.ts
import {Maybe} from "yup";

export interface RecipeInfo {
    id: number;
    imageURL: string;
    name: string;
    rating: number | null;
    reviews: number | null;
    description: string;
    tags: string[];
    recipeID:number
}

export interface UserProfileInfo {
    id: number;
    imageURL: string | null;
    name: string;
    followers: number;
    description: string;
    recipes: RecipeInfo[];
}

export interface AuthenticatedUserProfileInfo extends UserProfileInfo {
    followedUserIds: number[];
}


export interface AuthenticatedUserInfo {
    id: number;
    firstName?: Maybe<string | undefined>;
    lastName?: Maybe<string | undefined>;
    bio?: Maybe<string | undefined>;
    password?: Maybe<string | undefined>;
    userName: string;
    emailAddress: string;


}

export interface SearchResultRecipe {
    id: number;
    imageURL: string;
    name: string;
    rating: number | null;
    reviews: number | null;
    description: string;
    tags: string[];
    ingredients: string[];
}
