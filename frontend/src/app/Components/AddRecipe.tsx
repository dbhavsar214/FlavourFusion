'use client';
import React, { Children, useState, useEffect } from 'react';
import { useForm, SubmitHandler } from 'react-hook-form';
import * as yup from 'yup';
import { yupResolver } from '@hookform/resolvers/yup';
import { useRouter } from 'next/navigation';
import { useRef } from 'react';

interface IFormInput {
    name: string;
    dietaryPreference: string;
    description: string;
    ingredients: string[];
    tags: string[];
    direction: string;
    images: string[];
}

const dietaryOptions = [
    'Vegetarian',
    'Non-Vegetarian',
    'Vegan',
    'Flexitarian',
    'Pescatarian',
    'Pollotarian',
];

const schema = yup.object({
    name: yup.string().required('Recipe title is required'),
    dietaryPreference: yup.string().required('Please choose a dietary option'),
    description: yup.string().required('Bio is required'),
    ingredients: yup.array()
        .of(yup.string().required('Ingredient is required'))
        .min(1, 'At least one ingredient is required'),
    tags: yup.array()
        .of(yup.string().required('tags are required'))
        .min(1, 'At least one tag is required'),
    direction: yup.string().required('Add instructions'),
    images: yup.array()
        .of(yup.string().required('Image is required'))
        .min(1, 'At least 1 image is required')
}).required();

type addReciepeType = yup.InferType<typeof schema>

const AddRecipe: React.FC = () => {

    const router = useRouter();

    useEffect(() => {
        // Retrieve the session data
        const session = sessionStorage.getItem('session');
        let token = null;

        if (session) {
            const sessionData = JSON.parse(session);
            token = sessionData.token;
        }

        console.log('token', token);

        if (!token) {
            router.push('/login');
        }
    }, [router]);

    const { register, handleSubmit, formState: { errors }, setValue } = useForm<addReciepeType>({
        // resolver: yupResolver(schema),
        defaultValues: {
            name: '',
            dietaryPreference: '',
            description: '',
            ingredients: [''],
            direction: '',
            images: [''],
            tags: ['']
        }
    });

    const [ingredients, setIngredients] = useState<string[]>(['']);
    const [images, setimages] = useState<string[]>(['']);
    const [tags, setTags] = useState<string[]>(['']);
    const [dietaryPreference, setDietaryPreference] = useState<string>('');




    const handleAddIngredient = () => {
        setIngredients([...ingredients, '']);
    };

    const handleRemoveIngredient = (index: number) => {
        const updatedIngredients = ingredients.filter((_, idx) => idx !== index);
        setIngredients(updatedIngredients);
        setValue('ingredients', updatedIngredients as any); // Ensure form state is updated
    };

    const handleAddTag = () => {
        const newTags = [...tags, ''];
        setTags(newTags);
        setValue('tags', newTags);
    };

    const handleRemoveTag = (index: number) => {
        const updatedTags = tags.filter((_, idx) => idx !== index);
        setTags(updatedTags);
        setValue('tags', updatedTags);
    };


    const handleAddImage = () => {
        setimages([...images, '']);
    }

    const handleRemoveImage = (index: number) => {
        const updatedImages = images.filter((_, idx) => idx !== index);
        setimages(updatedImages);
        setValue('images', updatedImages as any);
    }

    const userIDRef = useRef(null);

    useEffect(() => {
        const session = sessionStorage.getItem('session');

        if (session) {
            const sessionData = JSON.parse(session);
            userIDRef.current = sessionData.userID;
        }

        console.log('userID', userIDRef.current);
    }, [router]);

    const onSubmit: SubmitHandler<addReciepeType> = async (data) => {
        console.log('Form data:', data);

        console.log("Submitting form data:", data);

        const modifiedData = {
            name: data.name,
            description: data.description,
            ingredients: data.ingredients,
            tags: [...(data.tags || []), data.dietaryPreference],
            creatorID: userIDRef.current,
            direction: data.direction,
        };
        console.log('modifiedData', modifiedData);
        try {
            const response = await fetch('api/addrecipe', { // Dummy API endpoint
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(modifiedData),
            });

            if (!response.ok) {
                throw new Error('Network response was not ok');
            }

            const result = await response.json();
            console.log('Success:', result);
        } catch (error) {
            console.error('Error:', error);
        }
    };

    const submit = handleSubmit(onSubmit);


    return (
        <div className="min-h-screen flex items-center justify-center bg-white p-6">
            <div className="bg-white p-10 rounded-lg shadow-2xl w-full max-w-2xl">
                <h2 className="text-4xl mb-8 text-center text-teal-600 font-bold">+ Add Recipe</h2>
                <form onSubmit={(e) => {
                    submit()
                }}>
                    <div className="mb-6">
                        <label className="block text-gray-600 text-sm font-medium mb-2" htmlFor="recipeTitle">
                            Recipe Title
                        </label>
                        <input
                            type="text"
                            id="name"
                            {...register('name')}
                            className={`shadow appearance-none border rounded-lg w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:shadow-outline ${errors.name ? 'border-red-500' : 'border-gray-300'}`}
                            placeholder="Recipe Title"
                        />
                        {errors.name && <p className="text-red-500 text-sm mt-1">{errors.name.message}</p>}
                    </div>
                    <div className="mb-6">
                        <label className="block text-gray-600 text-sm font-medium mb-2" htmlFor="dietaryPreference">
                            Choose a dietary preference:
                        </label>
                        <select
                            id="dietaryPreference"
                            {...register('dietaryPreference')}
                            className={`shadow appearance-none border rounded-lg w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:shadow-outline ${errors.dietaryPreference ? 'border-red-500' : 'border-gray-300'}`}
                        >
                            <option value="">--Please choose an option--</option>
                            {dietaryOptions.map((option) => (
                                <option key={option} value={option}>
                                    {option}
                                </option>
                            ))}
                        </select>
                        {errors.dietaryPreference && <p className="text-red-500 text-sm mt-1">{errors.dietaryPreference.message}</p>}
                    </div>
                    <div className="mb-6">
                        <label className="block text-gray-600 text-sm font-medium mb-2" htmlFor="bio">
                            A little info about the recipe
                        </label>
                        <textarea
                            id="description"
                            {...register('description')}
                            className={`shadow appearance-none border rounded-lg w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:shadow-outline ${errors.description ? 'border-red-500' : 'border-gray-300'}`}
                            placeholder="Tell us about the recipe"
                        />
                        {errors.description && <p className="text-red-500 text-sm mt-1">{errors.description.message}</p>}
                    </div>

                    <div className="mb-6">
                        <label className="block text-gray-600 text-sm font-medium mb-2">
                            Ingredients
                        </label>
                        {ingredients.map((ingredient, index) => (
                            <div key={index} className="flex mb-2">
                                <input
                                    {...register(`ingredients.${index}` as const)}
                                    type="text"
                                    className={`shadow appearance-none border rounded-lg w-6/12 py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:shadow-outline ${errors.ingredients?.[index] ? 'border-red-500' : 'border-gray-300'
                                        }`}
                                    placeholder={`Ingredient ${index + 1}`}
                                />
                                <button
                                    type="button"
                                    className="bg-teal-600 hover:bg-teal-700 text-white ml-2 font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
                                    onClick={handleAddIngredient}
                                >
                                    +
                                </button>
                                <button
                                    type="button"
                                    className="ml-2 bg-white border-solid border-2 border-teal-600 text-teal-600 font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
                                    onClick={() => handleRemoveIngredient(index)}
                                >
                                    -
                                </button>

                            </div>
                        ))}

                    </div>
                    <div className="mb-6">
                        <label className="block text-gray-600 text-sm font-medium mb-2" htmlFor="bio">
                            Gallery
                        </label>
                        <textarea
                            id="direction"
                            {...register('direction')}
                            className={`shadow appearance-none border rounded-lg w-full h-40 py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:shadow-outline ${errors.direction ? 'border-red-500' : 'border-gray-300'}`}
                            placeholder="Share us how you cooked it"
                        />
                        {errors.direction && <p className="text-red-500 text-sm mt-1">{errors.direction.message}</p>}
                    </div>

                    <div className="mb-6">
                        <label className="block text-gray-600 text-sm font-medium mb-2">
                            Tags
                        </label>
                        {tags.map((tag, index) => (
                            <div key={index} className="flex mb-2">
                                <input
                                    {...register(`tags.${index}` as const)}
                                    type="text"
                                    className={`shadow appearance-none border rounded-lg w-6/12 py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:shadow-outline ${errors.tags?.[index] ? 'border-red-500' : 'border-gray-300'
                                        }`}
                                    placeholder={`Tag ${index + 1}`}
                                />
                                <button
                                    type="button"
                                    className="bg-teal-600 hover:bg-teal-700 text-white ml-2 font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
                                    onClick={handleAddTag}
                                >
                                    +
                                </button>
                                <button
                                    type="button"
                                    className="ml-2 bg-white border-solid border-2 border-teal-600 text-teal-600 font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
                                    onClick={() => handleRemoveTag(index)}
                                >
                                    -
                                </button>
                            </div>
                        ))}
                        {errors.tags && <p className="text-red-500 text-sm mt-1">{errors.tags.message}</p>}
                    </div>

                    <div className="mb-6">
                        <label className="block text-gray-600 text-sm font-medium mb-2">
                            Gallery
                        </label>
                        {images.map((gallery, index) => (
                            <div key={index} className="flex mb-2">
                                <input
                                    {...register(`images.${index}` as const)}
                                    type="file"
                                    className={`shadow appearance-none border rounded-lg w-6/12 py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:shadow-outline ${errors.images?.[index] ? 'border-red-500' : 'border-gray-300'
                                        }`}
                                    placeholder={`Image ${index + 1}`}
                                />
                                <button
                                    type="button"
                                    className="bg-teal-600 hover:bg-teal-700 text-white ml-2 font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
                                    onClick={handleAddImage}
                                >
                                    +
                                </button>
                                <button
                                    type="button"
                                    className="ml-2 bg-white border-solid border-2 border-teal-600 text-teal-600 font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
                                    onClick={() => handleRemoveImage(index)}
                                >
                                    -
                                </button>

                            </div>
                        ))}

                    </div>
                    <div className="flex items-center justify-center">
                        <button
                            type="submit"
                            className="bg-teal-600 hover:bg-teal-700 text-white font-bold py-3 px-8 rounded-lg focus:outline-none focus:shadow-outline transition duration-200"
                        >
                            Add Recipe
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default AddRecipe;
