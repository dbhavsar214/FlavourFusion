'use client';

import React, { useState, useEffect } from 'react';
import { Box, Typography, TextField, Grid, Checkbox, FormControlLabel, Accordion, AccordionSummary, AccordionDetails, Chip } from '@mui/material';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import RecipeCard from './recipeCard';
import { styled } from '@mui/system';
import { search } from '@/services/searchService';
import { SearchResultRecipe } from '@/outputTypes/types';
import { useRouter, useSearchParams } from 'next/navigation';

const filters = {
    meals: ['Breakfast', 'Lunch', 'Dinner'],
    cuisines: ['Italian', 'Chinese', 'Indian'],
    ingredients: ['Chicken', 'Beef', 'Vegetables', 'Sugar', 'Parmesan', 'Butter'],
    equipment: ['Oven', 'Blender', 'Grill'],
    occasions: ['Christmas', 'Easter', 'Thanksgiving'],
    allergies: ['Gluten', 'Peanuts', 'Dairy'],
};

const FilterSection = styled(Box)({
    width: '100%',
    padding: '10px',
    marginBottom: '20px',
    overflowY: 'auto',
    maxHeight: '80vh',
});

const Sidebar = styled(Box)({
    position: 'sticky',
    top: '5px',  // Adjusted to avoid header overlap
    width: '300px',
    flexShrink: 0,  // Prevent the sidebar from shrinking
    borderRight: '1px solid #ccc',
    padding: '20px',
    backgroundColor: '#fff',
    overflowY: 'auto',
    boxShadow: '2px 0 5px rgba(0,0,0,0.1)',
    height: 'calc(100vh - 5px)',  // Adjust height to remain within viewport
});

const MainContent = styled(Box)({
    marginLeft: '80px',  // Adjusted for sidebar width
    padding: '20px',
    paddingTop: '80px',  // Padding to avoid overlap with header
    width: 'calc(100% - 320px)',  // Ensure main content takes the remaining width
});

const SearchPage = () => {
    const [searchTerm, setSearchTerm] = useState('');
    const [selectedFilters, setSelectedFilters] = useState<string[]>([]);
    const [ingredientFilters, setIngredientFilters] = useState<string[]>([]);
    const [equipmentSearchTerm, setEquipmentSearchTerm] = useState('');
    const [openCategories, setOpenCategories] = useState<{ [key: string]: boolean }>({
        meals: false,
        cuisines: false,
        ingredients: false,
        equipment: false,
        occasions: false,
        allergies: false,
    });
    const [recipes, setRecipes] = useState<SearchResultRecipe[]>([]);
    const [loading, setLoading] = useState(true);
    const router = useRouter();
    const searchParams = useSearchParams();

    useEffect(() => {
        const tags = searchParams.get('tags');
        const query = searchParams.get('query');
        if (tags) {
            const tagsArray = tags.split(',');
            setSelectedFilters(tagsArray);
        }
        if (query) {
            setSearchTerm(query);
        }
        // Fetch all recipes initially
        const fetchAllRecipes = async () => {
            try {
                const allRecipes = await search(query || '', '', '');
                console.log('Initial recipes:', allRecipes);  // Add this line for logging
                setRecipes(allRecipes);
                setLoading(false);
            } catch (error) {
                console.error('Error fetching all recipes:', error);
                setLoading(false);
            }
        };

        fetchAllRecipes();
    }, []);

    const handleSearchChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setSearchTerm(event.target.value);
    };

    const handleEquipmentSearchChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setEquipmentSearchTerm(event.target.value);
    };

    const handleFilterChange = (filter: string, isIngredient: boolean = false) => {
        if (isIngredient) {
            setIngredientFilters((prev) =>
                prev.includes(filter) ? prev.filter((f) => f !== filter) : [...prev, filter]
            );
        } else {
            setSelectedFilters((prev) =>
                prev.includes(filter) ? prev.filter((f) => f !== filter) : [...prev, filter]
            );
        }
    };

    const handleCategoryToggle = (category: string) => {
        setOpenCategories((prev) => ({
            ...prev,
            [category]: !prev[category],
        }));
    };

    const handleSearch = async () => {
        setLoading(true);
        try {
            const results = await search(searchTerm, selectedFilters.join(','), ingredientFilters.join(','));
            console.log('Search results:', results);  // Add this line for logging
            setRecipes(results);
            setLoading(false);
        } catch (error) {
            console.error('Error searching recipes:', error);
            setLoading(false);
        }
    };

    const handleTagClick = (tag: string) => {
        const tagsArray = selectedFilters.includes(tag) ? selectedFilters : [...selectedFilters, tag];
        setSelectedFilters(tagsArray);
        router.push(`/search?tags=${tagsArray.join(',')}`);
    };

    const handleTagRemove = (tag: string) => {
        const newFilters = selectedFilters.filter((f) => f !== tag);
        setSelectedFilters(newFilters);
        router.push(`/search?tags=${newFilters.join(',')}`);
    };

    useEffect(() => {
        handleSearch();
    }, [searchTerm, selectedFilters, ingredientFilters]);

    return (
        <Box display="flex">
            <Sidebar>
                {Object.keys(filters).map((category) => (
                    <FilterSection key={category}>
                        <Accordion expanded={openCategories[category]} onChange={() => handleCategoryToggle(category)}>
                            <AccordionSummary expandIcon={<ExpandMoreIcon />}>
                                <Typography>{category.charAt(0).toUpperCase() + category.slice(1)}</Typography>
                            </AccordionSummary>
                            <AccordionDetails>
                                {category === 'ingredients' && (
                                    <TextField
                                        fullWidth
                                        label="Search ingredients"
                                        variant="outlined"
                                        value={equipmentSearchTerm}
                                        onChange={handleEquipmentSearchChange}
                                        sx={{ mb: 2 }}
                                    />
                                )}
                                {filters[category as keyof typeof filters]
                                    .filter((filter) =>
                                        category === 'ingredients'
                                            ? filter.toLowerCase().includes(equipmentSearchTerm.toLowerCase())
                                            : true
                                    )
                                    .map((filter) => (
                                        <FormControlLabel
                                            key={filter}
                                            control={
                                                <Checkbox
                                                    checked={category === 'ingredients' ? ingredientFilters.includes(filter) : selectedFilters.includes(filter)}
                                                    onChange={() => handleFilterChange(filter, category === 'ingredients')}
                                                />
                                            }
                                            label={filter}
                                        />
                                    ))}
                            </AccordionDetails>
                        </Accordion>
                    </FilterSection>
                ))}
            </Sidebar>
            <MainContent>
                <Box sx={{ mb: 2, mr: 13 }}>
                    <TextField
                        fullWidth
                        label="Search Recipes"
                        variant="outlined"
                        value={searchTerm}
                        onChange={handleSearchChange}
                        sx={{ mb: 2 }}
                    />
                    <Box sx={{ mb: 2 }}>
                        {selectedFilters.map((filter) => (
                            <Chip
                                key={filter}
                                label={filter}
                                sx={{ mr: 1, mb: 1 }}
                                onClick={() => handleTagRemove(filter)}
                            />
                        ))}
                        {ingredientFilters.map((filter) => (
                            <Chip
                                key={filter}
                                label={filter}
                                sx={{ mr: 1, mb: 1 }}
                                onClick={() => handleTagRemove(filter)}
                            />
                        ))}
                    </Box>
                </Box>
                <Typography variant="h5" sx={{ mb: 2 }}>
                    {recipes.length} results found
                </Typography>
                <Grid container spacing={3} sx={{ pb: 4 }}>
                    {recipes.map((recipe, index) => (
                        <Grid item xs={12} sm={6} md={4} lg={3} key={index}>
                            <RecipeCard recipe={recipe} onTagClick={handleTagClick} />
                        </Grid>
                    ))}
                </Grid>
            </MainContent>
        </Box>
    );
};

export default SearchPage;
