'use client';
import React, { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation'; // Adjust the import to match your Next.js version
import { useForm, SubmitHandler } from 'react-hook-form';
import * as yup from 'yup';
import { yupResolver } from '@hookform/resolvers/yup';
import { fetchAuthenticatedUserDetails, updateUserProfile } from '@/services/userService';
import { AuthenticatedUserInfo } from '@/outputTypes/types';

type AuthenticatedUserFormInfo = Omit<AuthenticatedUserInfo, 'id'>;

const schema = yup.object({
    userName: yup.string().required('Username is required'),
    firstName: yup.string().notRequired(),
    lastName: yup.string().notRequired(),
    emailAddress: yup.string().email('Invalid email address').required('Email is required'),
    bio: yup.string().notRequired(),
    password: yup.string().min(8, 'Password must be at least 8 characters').notRequired(),
}).required();

const UpdateUser: React.FC<{ userId: string }> = ({ userId }) => {
    const router = useRouter();
    const { register, handleSubmit, setValue, formState: { errors } } = useForm<AuthenticatedUserFormInfo>({
        resolver: yupResolver(schema),
    });

    const [loading, setLoading] = useState(true);
    const [showPasswordFields, setShowPasswordFields] = useState(false);
    const [updateMessage, setUpdateMessage] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const [confirmNewPassword, setConfirmNewPassword] = useState('');

    useEffect(() => {
        const fetchData = async () => {
            const userDetails = await fetchAuthenticatedUserDetails('update') as AuthenticatedUserInfo;

            // Check if the user is authenticated and if the userId matches the authenticated user's ID
            if (!userDetails || userDetails.id.toString() !== userId) {
                router.push('/home'); // Redirect to the main page if not authenticated or ID doesn't match
                return;
            }

            if (userDetails) {
                setValue('userName', userDetails.userName);
                setValue('firstName', userDetails.firstName);
                setValue('lastName', userDetails.lastName);
                setValue('emailAddress', userDetails.emailAddress);
                setValue('bio', userDetails.bio);
            }
            setLoading(false);
        };

        fetchData();
    }, [userId, setValue, router]);

    const onSubmit: SubmitHandler<AuthenticatedUserFormInfo> = async (data) => {
        console.log('Form submitted:', data);
        try {
            if (showPasswordFields) {
                if (newPassword !== confirmNewPassword) {
                    alert('Passwords do not match');
                    return;
                }
                if (newPassword.length < 8) {
                    alert('Password must be at least 8 characters');
                    return;
                }
                data.password = newPassword;
            }

            // Manually add the userId to the data being submitted
            const updatedData: AuthenticatedUserInfo = {
                ...data,
                id: Number(userId),
            };

            await updateUserProfile(updatedData.id, updatedData);
            setUpdateMessage('User information updated successfully.');
            console.log('Update successful:', updatedData);
            setTimeout(() => {
                setUpdateMessage('');
                window.location.reload(); // Reload the page after the message disappears
            }, 3000);
        } catch (error) {
            console.error('Error:', error);
        }
    };

    if (loading) {
        return <div>Loading...</div>;
    }

    return (
        <div className="min-h-screen flex items-center justify-center bg-white p-6">
            <div className="bg-white p-10 rounded-lg shadow-2xl w-full max-w-2xl relative">
                <h2 className="text-4xl mb-8 text-center text-teal-600 font-bold">Update Details</h2>
                {updateMessage && (
                    <div className="absolute top-16 left-0 w-3/4 p-2 bg-green-100 text-green-700 border border-green-400 rounded-md text-center mt-6 mx-auto ml-20">
                        {updateMessage}
                    </div>
                )}
                <form onSubmit={handleSubmit(onSubmit)} className="mt-16">
                    <div className="mb-6">
                        <label className="block text-gray-600 text-sm font-medium mb-2" htmlFor="userName">
                            Username
                        </label>
                        <input
                            type="text"
                            id="userName"
                            {...register('userName')}
                            className={`shadow appearance-none border rounded-lg w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:shadow-outline ${errors.userName ? 'border-red-500' : 'border-gray-300'}`}
                            placeholder="Enter your username"
                        />
                        {errors.userName && <p className="text-red-500 text-sm mt-1">{errors.userName.message}</p>}
                    </div>
                    <div className="grid grid-cols-2 gap-6 mb-6">
                        <div>
                            <label className="block text-gray-600 text-sm font-medium mb-2" htmlFor="firstName">
                                First Name
                            </label>
                            <input
                                type="text"
                                id="firstName"
                                {...register('firstName')}
                                className={`shadow appearance-none border rounded-lg w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:shadow-outline ${errors.firstName ? 'border-red-500' : 'border-gray-300'}`}
                                placeholder="Enter your first name"
                            />
                            {errors.firstName && <p className="text-red-500 text-sm mt-1">{errors.firstName.message}</p>}
                        </div>
                        <div>
                            <label className="block text-gray-600 text-sm font-medium mb-2" htmlFor="lastName">
                                Last Name
                            </label>
                            <input
                                type="text"
                                id="lastName"
                                {...register('lastName')}
                                className={`shadow appearance-none border rounded-lg w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:shadow-outline ${errors.lastName ? 'border-red-500' : 'border-gray-300'}`}
                                placeholder="Enter your last name"
                            />
                            {errors.lastName && <p className="text-red-500 text-sm mt-1">{errors.lastName.message}</p>}
                        </div>
                    </div>
                    <div className="mb-6">
                        <label className="block text-gray-600 text-sm font-medium mb-2" htmlFor="emailAddress">
                            Email
                        </label>
                        <input
                            type="email"
                            id="emailAddress"
                            {...register('emailAddress')}
                            className={`shadow appearance-none border rounded-lg w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:shadow-outline ${errors.emailAddress ? 'border-red-500' : 'border-gray-300'}`}
                            placeholder="Enter your email"
                        />
                        {errors.emailAddress && <p className="text-red-500 text-sm mt-1">{errors.emailAddress.message}</p>}
                    </div>
                    <div className="mb-6">
                        <label className="block text-gray-600 text-sm font-medium mb-2" htmlFor="bio">
                            Bio
                        </label>
                        <textarea
                            id="bio"
                            {...register('bio')}
                            className={`shadow appearance-none border rounded-lg w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:shadow-outline ${errors.bio ? 'border-red-500' : 'border-gray-300'}`}
                            placeholder="Tell us about yourself"
                        />
                        {errors.bio && <p className="text-red-500 text-sm mt-1">{errors.bio.message}</p>}
                    </div>
                    <div className="mb-6">
                        <button
                            type="button"
                            className="bg-blue-600 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-lg focus:outline-none focus:shadow-outline transition duration-200"
                            onClick={() => setShowPasswordFields(!showPasswordFields)}
                        >
                            Change Password
                        </button>
                    </div>
                    {showPasswordFields && (
                        <div className="mb-6">
                            <div className="mb-6">
                                <label className="block text-gray-600 text-sm font-medium mb-2" htmlFor="newPassword">
                                    New Password
                                </label>
                                <input
                                    type="password"
                                    id="newPassword"
                                    value={newPassword}
                                    onChange={(e) => setNewPassword(e.target.value)}
                                    className={`shadow appearance-none border rounded-lg w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:shadow-outline ${newPassword.length < 8 ? 'border-red-500' : 'border-gray-300'}`}
                                    placeholder="Enter new password"
                                />
                                {newPassword.length < 8 && <p className="text-red-500 text-sm mt-1">Password must be at least 8 characters</p>}
                            </div>
                            <div className="mb-6">
                                <label className="block text-gray-600 text-sm font-medium mb-2" htmlFor="confirmNewPassword">
                                    Confirm New Password
                                </label>
                                <input
                                    type="password"
                                    id="confirmNewPassword"
                                    value={confirmNewPassword}
                                    onChange={(e) => setConfirmNewPassword(e.target.value)}
                                    className={`shadow appearance-none border rounded-lg w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:shadow-outline ${confirmNewPassword !== newPassword ? 'border-red-500' : 'border-gray-300'}`}
                                    placeholder="Confirm new password"
                                />
                                {confirmNewPassword !== newPassword && <p className="text-red-500 text-sm mt-1">Passwords do not match</p>}
                            </div>
                            <div className="flex items-center justify-center">
                                <button
                                    type="button"
                                    className="bg-gray-600 hover:bg-gray-700 text-white font-bold py-2 px-4 rounded-lg focus:outline-none focus:shadow-outline transition duration-200"
                                    onClick={() => {
                                        setShowPasswordFields(false);
                                        setNewPassword('');
                                        setConfirmNewPassword('');
                                    }}
                                >
                                    Cancel
                                </button>
                            </div>
                        </div>
                    )}
                    <div className="flex items-center justify-center">
                        <button
                            type="submit"
                            className="bg-teal-600 hover:bg-teal-700 text-white font-bold py-3 px-8 rounded-lg focus:outline-none focus:shadow-outline transition duration-200"
                        >
                            Update Details
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default UpdateUser;
