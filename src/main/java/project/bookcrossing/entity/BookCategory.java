package project.bookcrossing.entity;

public enum BookCategory {
	Biography, ChildrenBook, Guide, PopularScience, Thriller, Novel, Poetry, History, Romance, Education, Scientific, Adventure, Criminal, Humour, Science_fiction, Other;

	public static BookCategory getEnumCategory(String category) {
		switch (category) {
			case "Biography":
				return BookCategory.Biography;
			case "ChildrenBook":
				return BookCategory.ChildrenBook;
			case "Guide":
				return BookCategory.Guide;
			case "PopularScience":
				return BookCategory.PopularScience;
			case "Thriller":
				return BookCategory.Thriller;
			case "Novel":
				return BookCategory.Novel;
			case "Poetry":
				return BookCategory.Poetry;
			case "History":
				return BookCategory.History;
			case "Romance":
				return BookCategory.Romance;
			case "Education":
				return BookCategory.Education;
			case "Scientific":
				return BookCategory.Scientific;
			case "Adventure":
				return BookCategory.Adventure;
			case "Criminal":
				return BookCategory.Criminal;
			case "Humour":
				return BookCategory.Humour;
			case "Science_fiction":
				return BookCategory.Science_fiction;
			default:
				return BookCategory.Other;
		}
	}
}
