package edu.ucne.Heyson_polanco_ap2_p2.data

import edu.ucne.Heyson_polanco_ap2_p2.data.remote.RemoteDataSource
import edu.ucne.Heyson_polanco_ap2_p2.domain.repository.Repository
import javax.inject.Inject

class RepositoryImp @Inject constructor(
    private val remoteDataSource: RemoteDataSource
): Repository {
}