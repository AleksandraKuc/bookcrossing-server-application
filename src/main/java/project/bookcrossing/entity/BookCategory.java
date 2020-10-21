package project.bookcrossing.entity;

public enum BookCategory {
	Biography, ChildrenBook, Guide, PopularScience, Thriller, Novel, Poetry, History, Romance, Education, Scientific, Adventure, Criminal, Humour, Science_fiction, Other;

	public static BookCategory getEnumCategory(String category) {
		switch (category.toLowerCase()) {
			case "biography":
				return BookCategory.Biography;
			case "childrenbook":
				return BookCategory.ChildrenBook;
			case "guide":
				return BookCategory.Guide;
			case "popularscience":
				return BookCategory.PopularScience;
			case "thriller":
				return BookCategory.Thriller;
			case "novel":
				return BookCategory.Novel;
			case "poetry":
				return BookCategory.Poetry;
			case "history":
				return BookCategory.History;
			case "romance":
				return BookCategory.Romance;
			case "education":
				return BookCategory.Education;
			case "scientific":
				return BookCategory.Scientific;
			case "adventure":
				return BookCategory.Adventure;
			case "criminal":
				return BookCategory.Criminal;
			case "humour":
				return BookCategory.Humour;
			case "science_fiction":
				return BookCategory.Science_fiction;
			default:
				return BookCategory.Other;
		}
	}
}
