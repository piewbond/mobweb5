package hu.bme.aut.android.jot.domain.usecases

import hu.bme.aut.android.jot.data.repository.ExcerciseRepository

class ExcerciseUseCases(repository: ExcerciseRepository) {
    val loadExcercises = LoadExcercisesUseCase(repository)
    val loadExcercise = LoadExcerciseUseCase(repository)
    val saveExcercise = SaveExcerciseUseCase(repository)
    val updateExcercise = UpdateExcerciseUseCase(repository)
    val deleteExcercise = DeleteExcerciseUseCase(repository)
}